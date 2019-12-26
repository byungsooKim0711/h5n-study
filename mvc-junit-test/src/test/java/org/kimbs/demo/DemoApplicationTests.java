package org.kimbs.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = DemoApplicationTests.ContainerPropertyInitializer.class)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Value("${container.port}")
    private int containerPort;

    @LocalServerPort
    private int port;

    private StringBuilder defaultUrl = new StringBuilder();
    private static HttpHeaders headers = new HttpHeaders();

    @Container
//    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer().withDatabaseName("membertest");
//    private static GenericContainer postgreSQLContainer = new GenericContainer("postgres").withExposedPorts(5432).withEnv("POSTGRES_DB", "membertest");
    private static DockerComposeContainer composeContainer = new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
            .withExposedService("member-db", 5432);


    @BeforeAll
    public static void setUp() {
        headers.set("ORG-KIMBS-VERSION", "v1");
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
//        postgreSQLContainer.followOutput(logConsumer);
    }

    @BeforeEach
    public void init() {
        defaultUrl.append("http://localhost:").append(port).append("/api/member");

//        System.out.println(postgreSQLContainer.getLogs());
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@" + environment.getProperty("container.port"));
//        System.out.println("======================" + containerPort);
    }

    @AfterEach
    public void tearDown() {
        defaultUrl.setLength(0);
    }

    @Test
    @DisplayName("통합테스트 FindAll()")
    public void testFindAll() {
        /* arrange */
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        /* act */
        ResponseEntity<List<Member>> members = this.restTemplate.exchange(
                defaultUrl.toString()
                , HttpMethod.GET
                , entity
                , new ParameterizedTypeReference<List<Member>>() {}
        );

        /* assert */
        assertEquals(HttpStatus.OK, members.getStatusCode());
        assertEquals(Collections.emptyList(), members.getBody());
    }

    @Test
    @DisplayName("통합테스트 createMember")
    public void testCreate() {
        /* arrange */
        Member member = new Member();
        member.setName("kbs");
        member.setEmail("kbs@humuson.com");
        member.setScore(100);

        final HttpEntity<Member> entity = new HttpEntity<Member>(member, headers);

        /* act */
        ResponseEntity<Member> responseEntity = this.restTemplate.exchange(
                defaultUrl.toString()
                , HttpMethod.POST
                , entity
                , Member.class
        );

        /* assert */
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertEquals("/api/member/1", responseEntity.getHeaders().getLocation().getPath()),
                () -> assertEquals(member.getName(), responseEntity.getBody().getName()),
                () -> assertEquals(member.getScore(), responseEntity.getBody().getScore()),
                () -> assertEquals(member.getEmail(), responseEntity.getBody().getEmail())
        );
    }

    @Test
    @DisplayName("통합테스트 NotFound")
    public void testNotFound() {
        /* arrange */
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        /* act */

        /* assert */
        ResponseEntity<Void> deleteResponse = this.restTemplate.exchange (
                defaultUrl.append("/123456789").toString()
                , HttpMethod.DELETE
                , entity
                , Void.class
        );

        /* assert */
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
//            TestPropertyValues.of("container.port=" + postgreSQLContainer.getMappedPort(5432)).applyTo(applicationContext.getEnvironment());
            TestPropertyValues.of("container.port=" + composeContainer.getServicePort("member-db", 5432)).applyTo(applicationContext.getEnvironment());
        }
    }
}