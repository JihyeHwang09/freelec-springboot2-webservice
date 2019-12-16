package com.phang.book.springboot.config.auth.dto;

import com.phang.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    /*
    SessionUser에서는 인증된 사용자 정보만 필요하다.
    그 외에 필요한 정보들이 없으므로 name, email, picture만 필드로 선언한다.
     */
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
