package com.phang.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@SpringBootApplication
: 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
 @SpringBootApplication가 있는 위치부터 설정을 읽어가기 때문에
 -> 반드시 항상 프로젝트의 최상단에 위치해야 한다!
 */

/*
 EnableJpaAuditing을 사용하기 위해서는 최소 하나의 @Entity 클래스가 필요하다.
 @WebMvcTest이다보니 당연히 없다.
 @EnableJpaAuditing이 @SpringBootApplication과 함께 있다보니
 @WebMvcTest에서도 스캔하게 되었다.
 -> @EnableJpaAuditin과  @SpringBootApplication 분리
 */
//@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run()로 인해 내장 WAS(web Application Server)를 실행한다.

        /*
        cf) 내장 WAS
        1. 내장 WAS의 의미
          내장 WAS 별도로 외부에 WAS를 두지 않고,
          애플리케이션을 실행할 때 내부에서 WAS를 실행하는 것을 의미
         -> 항상 서버에 톰캣을 설치할 필요가 없어진다.
         스프링부트로 만들어진 Jar 파일(실행 가능한 Java 패키징 파일)로 실행하면 된다.

        2. 내장 WAS 사용을 권장하는 이유
        : 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있기 때문이다.
        (cf) 외장 WAS를 사용할 경우, 모든 서버는 WAS의 종류와 버전, 설정을 일치시켜야만 한다.
        새로운 서버가 추가될 경우, 모든 서버가 같은 WAS 환경을 구축해야만 한다.
        -> 실수할 여지도 많고, 시간도 많이 필요한 큰 작업이 될 수 있다.
        -> 내장 WAS를 사용하여 이 문제를 해결 가능!
        */
        SpringApplication.run(Application.class, args);
    }
}
