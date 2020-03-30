package org.kimbs.reactor.netty.client;

import lombok.extern.slf4j.Slf4j;
import org.kimbs.reactor.netty.client.auth.AuthorizeHandler;
import org.kimbs.reactor.netty.packet.Packet;
import org.springframework.context.ApplicationEvent;
import reactor.netty.Connection;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpClient;

import javax.annotation.PreDestroy;

@Slf4j
public abstract class AbstractClient {

    // thread-name-prefix, worker-thread-count, daemon
    private LoopResources loop = LoopResources.create("kbs-loop", 1, true);

    // thread-name-prefix, connection-pool-size
    private ConnectionProvider provider = ConnectionProvider.create("kbs-provider", 1);
    private Connection connection;

    protected abstract void onEventListener(ApplicationEvent event) throws Exception;

    protected void connect(String host, int port) throws Exception {
        this.connection = TcpClient.create(this.provider)
                .host(host)
                .port(port)
                .doOnConnected(conn -> {
                    // 비동기로 빠짐
                    log.info("[DO ON CONNECTED]");
                    conn.addHandlerLast("encoder", new AuthorizeHandler());
                })
                .runOn(this.loop)
                .connectNow();

        if (this.connection.isPersistent()) {
            log.info("[CONNECTION SUCCESS]");
            this.authRequestHook();
        }
    }

    @PreDestroy
    protected void disconnect() throws Exception {
        this.connection.onDispose().block();
        this.loop.dispose();
    }

    protected abstract void authRequestHook() throws Exception;

    protected void sendPacket(Packet<?> packet) throws Exception {
        // 패킷 전송은 어떻게 하는걸까..?
    }
}
