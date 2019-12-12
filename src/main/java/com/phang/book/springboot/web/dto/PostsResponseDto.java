package com.phang.book.springboot.web.dto;

import com.phang.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    /*
     PostsResponseDto는 Entity 필드 중 일부만 사용한다.
     -> 생성자로 Entity를 받아 필드에 값을 넣는다.
     굳이 모든 필드를 가진 생성자가 필요하지는 않으므로
     -> Dto는 Entity를 받아서 처리한다.
     */
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}


