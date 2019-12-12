package com.phang.book.springboot.web.dto;

import com.phang.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
/*
 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안 된다!
 Entity클래스는 데이터베이스와 맞닿은 핵심 클래스
 */