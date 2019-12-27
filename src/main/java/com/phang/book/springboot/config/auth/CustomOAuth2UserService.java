package com.phang.book.springboot.config.auth;

import com.phang.book.springboot.config.auth.dto.OAuthAttributes;
import com.phang.book.springboot.config.auth.dto.SessionUser;
import com.phang.book.springboot.domain.user.User;
import com.phang.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
         registrationId
            - 현재 로그인 진행 중인 서비스를 구분하는 코드
            - 지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동 시에
            네이버 로그인읹, 구글 로그인인지 구분하기 위해 사용한다.
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        /*
        userNameAttributeName
            - OAuth2 로그인 진행 시, 키가 되는 필드값(Primary Key와 같은 의미)
            - 구글의 경우, 기본적으로 코드를 지원하지만, 네이버, 카카오 등은 기본 지원하지 않는다.
            구글의 기본 코드는 "sub"이다.
            - 이후, 네이버 로그인과 구글 로그인을 동시 지원할 때 사용한다.

         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        /*
        OAuthAttributes
            - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
            - 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용한다.
         */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        /*
        SessionUser
             - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
             - 왜 User 클래스를 쓰지 않고 새로 만들어서 쓰는지?
         */
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
    /*
     구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현됨
     사용자의 이름이나 프로필 사진이 변경되면, User 엔티티에도 반영된다.
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
/*
    User 클래스는 엔티티이다.
    엔티티 클래스에는 언제 다른 엔티티와 관계가 형서오딜지 모른다.
    ex) @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있다면,
    직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다.

    -> 그래서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 것이
     이후 운영 및 유지보수 때 많은 도움이 된다.
 */