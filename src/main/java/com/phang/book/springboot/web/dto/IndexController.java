package com.phang.book.springboot.web.dto;

import com.phang.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
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
    public String index(Model model) {
        /*
        Model
        - 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        - 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
         */
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
//      src/main/resources/templates/posts-save.mustache가 호출된다.
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
