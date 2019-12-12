package com.phang.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

/*
주요 어노테이션을 클래스에 가깝게 둔다.

@Entity: JPA의 어노테이션
@Getter,@NoArgsConstructor: 롬복의 어노테이션

롬복은 코드를 단순화시켜주지만, 필수 어노테이션은 아니다.
-> 따라서, 주요 어노테이션인 @Entity를 클래스에 가깝게 두고, 롬복 어노테이션을 그 위로 두었다.
-> 이렇게 할 경우, 코틀린 등의 새 언어 전환으로 롬복이 더 이상 필요 없을 경우, 쉽게 삭제할 수 있다.
 */

//@Getter: 클래스 내 모든 필드의 Getter 메서드를 자동 생성
@Getter

/*
@NoArgsConstructor
    - 기본 생성자 자동 추가
    - public Posts(){}와 같은 효과
 */
@NoArgsConstructor
/*
@Entity
    - 테이블과 링크될 클래스임을 나타낸다.
    - 기본값으로 클래스의 CamelCase 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다.
    - ex) SalesManager.java -> sales_manager table

    - Entity 클래스에서는 절대!!! Setter 메서드를 만들지 않는다!!!
    (대신, 해당 필드의 값 변경이 필요하면, 명확히 그 목적과 의도를 나타낼 수 있는 메서드를 추가해야 한다.)
*/

@Entity
public class Posts {
/*
    Posts 클래스
        - 실제 DB의 테이블과 매칭될 클래스. 보통 Entity 클래스라고 부른다.
        - JPA 사용시, DB 데이터에 작업할 경우, 이 Entity 클래스의 수정을 통해 작업한다.
 */

//    @Id: 해당 테이블의 PK 필드
    @Id
/*
   @GeneratedValue(strategy = GenerationType.IDENTITY)
        - PK의 생성 규칙을 나타낸다.
        - 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만,
        auto_increment가 된다.
 */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    @Column
        - 테이블의 컬럼을 나타내며, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 된다.
        - 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
        - 문자열의 경우, VARCHAR(255)가 기본값인데, 사이즈를 500(ex) title),
        타입을 TEXT로 변경하고 싶거나(ex) content) 등의 경우에 사용된다.
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;


    /*
    @Builder
        - 해당 클래스의 빌더 패턴 클래스를 생성
        - 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    */
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
/*
Q) Setter가 없는데, 이 상황에서 어떻게 값을 채워서 DB에 삽입하는가?
    - 기본 구조: 생성자를 통해 최종값을 채운 후, DB에 삽입하는 것
    - 값 변경이 필요한 경우: 해당 이벤트에 맞는 public 메서드를 호출하여 변경한다.

    - 여기에서는 생성자 대신에 @Builder를 통해 제공되는 빌더 클래스를 사용한다.
Q) 생성자가 아닌 빌더 클래스를 사용하는 이유?
    - 생성자의 경우 지금 채워야 할 필드가 무엇인지 명확하게 지정할 수 없다는 문제가 있다.
        ex) new Exmample(b,a)처럼 a와 b의 위치를 변경해도 코드를 실행하기 전까지는 문제를 찾을 수 없다.

    - But, 빌더를 사용하면, 어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있다.
 */

