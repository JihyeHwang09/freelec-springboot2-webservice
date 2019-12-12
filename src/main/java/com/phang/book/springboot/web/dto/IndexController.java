package com.phang.book.springboot.web.dto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    /*
    build.gradle에 설정한 머스테치 스타터 덕분에 문자열을 반환할 때,
    앞의 경로, 뒤의 파일 확장자가 자동으로 지정된다.
    - 앞의 경로: src/main/resources/templates
    - 뒤의 파일 확장자: src/main/resources/templates/index.mustache
    로 전환되어 View Resolver가 처리하게 된다.

    View Resolver: URL 요청의 결과를 전달할 타입과 값을 지정하는 관리자 격으로 볼 수 있다.
     */
    // 스프링 부트는 기본적으로  src/main/resources/static에 위치한 자바스크립트, CSS, 이미지 등의
    //  정적 파일들은 URL에서 /로 설정된다.
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
//      src/main/resources/templates/posts-save.mustache가 호출된다.
        return "posts-save";
    }
}
