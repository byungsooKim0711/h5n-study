package org.kimbs.uracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.uracker.controller.RoutingFilterFunction;
import org.kimbs.uracker.controller.UrackerRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrackerApplicationTests {

	@Autowired
	private WebTestClient webClient;

//	public UrackerApplicationTests(WebTestClient webclient) {
//		this.webClient = webclient;
//	}

	@Test
	void unknownTargetId() {
		webClient
				.get()
				.uri("/{id}", "asdf")
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

}
