package com.phang.book.springboot.web.dto;

import com.phang.book.springboot.config.auth.LoginUser;
import com.phang.book.springboot.config.auth.dto.SessionUser;
import com.phang.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    private final HttpSession httpSession;
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
    public String index(Model model, @LoginUser SessionUser user) {
        /*
        Model
        - 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        - 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
         */
        /*
        @LoginUser SessionUser user
            - 가존에 (User)httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선되었다.
            - 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었다.
         */
        model.addAttribute("posts", postsService.findAllDesc());

        /*
         (SessionUser) httpSession.getAttribute("user")
            - CustomOAuth2UserService에서 로그인 성공 시, 세션에 SessionUser를 저장하도록 구성했음
            - -> 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.
         */
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) { // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다.
//            세션에 저장된 값이 없으면 model에는 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
           model.addAttribute("userName", user.getName());
        }
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
