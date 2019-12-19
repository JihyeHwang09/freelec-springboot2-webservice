package com.phang.book.springboot.web;

import com.phang.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
@RunWith(SpringRunner.class)
    - 이 어노테이션을 붙여줘야 스프링 테스트를 JUnit으로 돌릴 수 있다.
    - 테스트 진행시, JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
    - 여기서는 SpringRunner라는 실행자를 실행시킨다.
    --> 즉, 스프링 부트 테스트와 JUnit 사이의 연결자 역할을 한다.
 */
@RunWith(SpringRunner.class)
/*
@WebMvcTest
    - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC에 집중할 수 있는 어노테이션)
    - 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
    - 여기서는 컨트롤러만 사용하기 때문에 선언한다.

    - WebSecurityConfigurerAdapter, WebMvcConfigurer를 비롯한 @ControllerAdvice, @Controller를 읽는다.
    - -> 즉, @Service, @Component는 스캔 대상이 아니다!
    - -> 이 문제를 해결하기 위해서 스캔 대상에서 SecurityConfig를 제거한다.
 */
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class HelloControllerTest {

//    @Autowired: 스프링이 관리하는 빈(Bean)을 주입 받는다.
    /*
    private MockMvc mvc
    - 웹 API를 테스트할 때 사용
    - 스프링 MVC 테스트의 시작점
    - 이 클래스를 통해서 HTTP GET, POST 등에 대한 테스트를 할 수 있다.
     */

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER") // 가짜로 인증된 사용자를 생성
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        /*
         mvc.perform(get("/hello"))
            - MockMvc를 통해 /hello 주소로 GET 요청을 한다.
            - 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.
         */

        mvc.perform(get("/hello"))
/*
               .andExpect(status().isOk())
                    - mvc.perform의 결과를 검증
                    - HTTP Header의 Status를 검증
                    - 우리가 흔히 알고 있는 20, 404, 500 등의 상태를 검증
                    - 여기에서는 OK 즉, 200인지 아닌지를 검증한다.
 */
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
 /*
                .andExpect(content().string(hello))
                    - mvc.perform의 결과를 검증
                    - 응답 내용의 본문을 검증
                    - Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
*/
    }

    @WithMockUser(roles = "USER") // 가짜로 인증된 사용자를 생성
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                    /*
                    param
                        - API 테스트할 때, 사용될 요청 파라미터를 설정한다.
                        - 단, 값은 String만 허용된다!
                        - -> 따라서, 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능하다.
                     */
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                /*
                jsonPath
                    - JSON 응답값을 필드별로 검증할 수 있는 메서드
                    - $를 기준으로 필드명을 명시한다.
                    - 여기서는 name과 amount를 검증하므로 $.name, $.amount로 검증한다.
                 */
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
