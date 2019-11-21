package org.kimbs.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("통합테스트 FindAll()")
    public void testFindAll() {
        /* arrange */

        /* act */
        ResponseEntity<List<Member>> members = this.restTemplate.exchange(
                "http://localhost:" + this.port + "/api/member"
                , HttpMethod.GET
                , null
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

        /* act */
        ResponseEntity<Member> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + this.port + "/api/member"
                , member
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
}