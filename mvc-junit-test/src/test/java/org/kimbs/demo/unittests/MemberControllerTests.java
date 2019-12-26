package org.kimbs.demo.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kimbs.demo.controller.v1.MemberController;
import org.kimbs.demo.exception.MemberNotFoundException;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.service.MemberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MemberControllerTests {

    private static ObjectMapper mapper;

    @InjectMocks
    private MemberController controller;

    @Mock
    private MemberService service;

    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("컨트롤러 멤서 삽입 테스트")
    public void testInsertMember() throws Exception {
        /* arrange */
        Member member = new Member();
        member.setId(9999L);
        member.setScore(100);
        member.setName("kbs");
        member.setEmail("kbs0711@humuson.com");

        when(service.create(any(Member.class))).thenReturn(member);
        String jsonString = mapper.writeValueAsString(member);

        /* act & assert */
        mockMvc.perform(
                post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("ORG-KIMBS-VERSION", "v1"))
                .andExpect(status().isCreated())
//                .andExpect(header().string("location", Matchers.containsString("http://localhost/api/member/" + member.getId())))
                .andExpect(redirectedUrlPattern("http://*/api/member/" + member.getId()))
                .andExpect(content().string(jsonString));

        verify(service, times(1)).create(any(Member.class));
        verify(service, times(1)).create(notNull());
        verify(service, times(1)).create(isNotNull());
    }

    @Test
    @DisplayName("멤버 ID로 조회를 하지 못할 경우 404 Not found 테스트")
    public void testFindByIdFailWith404NotFound() throws Exception {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setScore(100);
        member.setName("kbs");
        member.setEmail("kbs0711@humuson.com");

        when(service.findById(member.getId())).thenThrow(new MemberNotFoundException());

        /* act & assert */
        mockMvc.perform(
                get("/api/member/{memberId}", member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("ORG-KIMBS-VERSION", "v1")
                .content(mapper.writeValueAsString(member)))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(member.getId());
    }

    @Test
    public void test() throws Exception {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setScore(100);
        member.setName("kbs");
        member.setEmail("kbs0711@humuson.com");

        when(service.findById(member.getId())).thenReturn(member);

        /* act & assert*/
        mockMvc.perform(
                get("/api/member/{memberId}", member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("ORG-KIMBS-VERSION", "v1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(member)));

        verify(service, times(1)).findById(member.getId());
    }
}
