package com.phang.book.springboot.web;

import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/*
 이 컨트롤러는 특별히 스프링 환경이 필요하지 않다!
 -> @SpringBootTest 없이 테스트 코드를 작성한다.
 */
/*
    ProfileController나 Environment 모두 자바 클래스(인터페이스)이기 때문에 쉽게 테스트할 수 있다.
Environment는 인터페이스와 가짜 구현체인 MockEnvironment(스프링에서 제공)를 사용해서 테스트하면 된다.

    이렇게 해보면, 생성자 DI가 얼마나 유용한지 알 수 있다.
만약 Environment를 @Autowired로 DI 받았다면 이런 테스트 코드를 작성하지 못했다.
항상 스프링 테스트를 해야만 했을 것이다.
앞의 테스트가 성공적으로 다 통과했다면, 컨트롤러 로직에 대한 이슈는 없다.
 */
public class ProfileControllerUnitTest {

    @Test
    public void real_profile이_조회된다() {
        // given
        String exptectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(exptectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(exptectedProfile);
    }

    @Test
    public void real_profile이_없으면_첫_번째가_조회된다() {
        // given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile이_없으면_default가_조회된다() {
        // given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
