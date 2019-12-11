package com.phang.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 Junit의 기본 assertThat이 아닌, assertj의 assertThat을 사용했음
 assertj 역시 Junit에서 자동으로 라이브러리를 등록해준다.

 Junit과 비교하여 assertj의 장점
    - coreMatchers와 다르게, 추가적으로 라이브러리가 필요하지 않다.
        - Junit의 assertThat을 사용하면, is()와 같이 coreMatchers 라이브러리가 필요하다.
    - 자동완성이 좀 더 확실하게 지원된다.
        - IDE에서는 coreMatchers와 같은 Matchers 라이브러리의 자동완성 지원이 약하다.
 */


public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        /*
        assertThat
            - assertj라는 테스트 검증 라이브러리의 검증 메서드
            - 검증하고 싶은 대상을 인자로 받는다.
            - 메서드 체이닝이 지원되어 -> isEqualTo와 같이 메서드를 이어서 사용 가능하다.
         */
        assertThat(dto.getName()).isEqualTo(name);
        /*
        isEqualTo
            - assertj의 동등 비교 메서드
            - assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공이다.
         */
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
