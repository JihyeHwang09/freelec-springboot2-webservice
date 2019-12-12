package com.phang.book.springboot.service.posts;

import com.phang.book.springboot.domain.posts.Posts;
import com.phang.book.springboot.domain.posts.PostsRepository;
import com.phang.book.springboot.web.dto.PostsResponseDto;
import com.phang.book.springboot.web.dto.PostsSaveRequestDto;
import com.phang.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

}
/*
    update 기능에서 데이터베이스에 쿼리를 날리는 부분이 없다.
    가능한 이유?
    JPA의 영속성 컨텍스트 때문.

    영속성 컨텍스트
        - 엔티티를 영구 저장하는 환경(일종의 논리적인 개념)

    JPA의 핵심 내용
        - 엔티티가 영속성 컨텍스트에 포함되어 있는가 아닌가로 갈린다.
        JPA의 엔티티 매니저가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션)
        트랜잭션 안에서 데이터베이스에서 데이터를 가져오면,
        이 데이터는 영속성 컨텍스트가 유지된 상태이다.
        이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에
        해당 테이블에 변경분을 반영한다.
        -> 즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다.
        이 개념을 더티 체킹이라고 한다.
 */
