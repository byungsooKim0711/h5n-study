package org.kimbs.rsocket.integration;

import org.kimbs.rsocket.protocol.GreetingRequest;
import org.kimbs.rsocket.protocol.GreetingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.rsocket.ClientRSocketConnector;
import org.springframework.integration.rsocket.RSocketInteractionModel;
import org.springframework.integration.rsocket.dsl.RSocketOutboundGatewaySpec;
import org.springframework.integration.rsocket.dsl.RSockets;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.rsocket.RSocketStrategies;

import java.io.File;

/**
 * inbound adapters: (real world) -> Message<T>
 * outbound adapters: Message<T> -> (real world)
 * split, route, transform, the enrich, the wireTap, aggregate
 * (real world) -> Spring Integration -> (real world): inbound gateway
 * (Spring Integration) -> real world -> (Spring Integration): outbound gateway
*/

@SpringBootApplication
public class RsocketIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketIntegrationApplication.class, args);
    }

    @Bean
    ClientRSocketConnector clientRSocketConnector(RSocketStrategies strategies) {
        ClientRSocketConnector clc = new ClientRSocketConnector("localhost", 8888);
        clc.setRSocketStrategies(strategies);
        return clc;
    }

    // file | rsocket | log

    @Bean
    MessageChannel reactiveMEsageChannel() {
        return MessageChannels.flux().get();
    }

    @Bean
    IntegrationFlow rsocketFlow(ClientRSocketConnector connector, @Value("${user.dir}") File home) {

        // file inbound adapter
        File folder = new File (new File(home, "file"), "in");
        FileReadingMessageSource fileReadingMessageSource = Files
                .inboundAdapter(folder)
                .autoCreateDirectory(true)
                .get();

        // RSocket outbound gateway
        RSocketOutboundGatewaySpec rsocket = RSockets
                .outboundGateway("greetings")
                .interactionModel(RSocketInteractionModel.requestStream)
                .clientRSocketConnector(connector)
                .expectedResponseType(GreetingResponse.class);

        return IntegrationFlows
                .from(fileReadingMessageSource, pmc -> pmc.poller(pm -> pm.fixedRate(1_1000)))
                .transform(new FileToStringTransformer())
                .transform(String.class, name -> new GreetingRequest(name.trim()))
                .handle(rsocket)
                .split()
                .channel(reactiveMEsageChannel())
                .handle((GenericHandler<GreetingResponse>) (greetingResponse, messageHeaders) -> {
                    System.out.println("new message: " + greetingResponse.toString());
                    return null;
                })
                .get();
    }

}
