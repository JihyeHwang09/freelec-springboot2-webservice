package com.phang.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//@Getter: 선언된 모든 필드의 get 메서드를 생성해준다.
/*
@RequiredArgsConstructor
    - 선언된(초기화되지 않은) 모든 final 필드가 포함된 생성자를 자동 생성
    - final이 없는 필드는 생성자에 포함되지 않는다.
*/
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
   private final String name;
   private final int amount;
}
