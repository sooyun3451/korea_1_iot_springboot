package org.example.springbootdeveloper.controller;

import org.example.springbootdeveloper.entity.Member;
import org.example.springbootdeveloper.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
// 메인 애플리케이션 클래스에 있는 @SpringBootApplication 을 찾고 해당 클래스의 빈을 찾아 테스트용 애플리케이션 컨택스트를 생성
@AutoConfigureMockMvc
// MockMvc 를 생성하고 자동으로 구성하는 어노테이션
// : 컨트롤러 테스트 시 사용
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        // 테스트 실행 전 메서드: MockMvc 설정
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void cleanUp() {
        // member 테이블에 있는 데이터를 모두 삭제
        memberRepository.deleteAll();
    }

    // 실제 테스트 코드 작성
    @DisplayName("getAllMembers: 조회 성공")
    @Test
    public void getAllMembers() throws Exception {
        // given
        final String url = "/test";
        Member saveMember = memberRepository.save(new Member(1L, "홍길동"));

        // when
        // .perform() 메서드: 요청을 전송하는 역할
        // - 결과를 resultActions 객체로 받음
        // - ResultActions 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공
        final ResultActions result = mockMvc.perform(get(url)
                // .accept(): 요청을 보낼 때 무슨 타입으로 응답 받을지 결정하는 메서드
                // - JSON, XML 등이 있음
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                // andExpect() 메서드: 응답을 검증
                // - TestController 에서 만든 API 는 응답으로 OK(200)을 반환
                // - .isOk() 메서드로 응답코드가 맞는지 확인
                .andExpect(status().isOk())
                // jsonPath("$[0].필드명")
                // : JSON 의 응답값을 가져오는 역할을 담당
                // : 0변째 배열에 들어있는 객체의 id, name 을 가져오고 저장된 값과 같은지 확인
                .andExpect(jsonPath("$[0].id").value(saveMember.getId()))
                .andExpect(jsonPath("$[0].name").value(saveMember.getName()));
    }
}

// HTTP 주요 응답 코드
// - 200 OK: isOK()
// - 201 Created: isCreated()
// - 400 Bad Request: isBadRequest()
// - 403 Forbidden: isForbidden()
// - 404 Not Found: isNotFound()
// - 400번대 응답 코드: is4xxClientError()
// - 500 Internal Server Error: isInternalServerError()
// - 500번대 응답 코드: is5xxServerError()