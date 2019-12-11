package com.phang.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Posts 클래스로 Database를 접근하게 해주는 클래스
/*
JpaRepository<Entity 클래스, PK타입>을 상속하면, 기본적인 CRUD 메서드가 자동으로 생성된다.
@Repository를 추가할 필요도 없다!!

주의할 점: Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다!!!
(둘은 아주 밀접한 과녜이고, 기본 Repository 없이는 제대로 역할을 할 수가 없다.)
-> 프로젝트 규모가 커져서 도메인별로 프로젝트를 분리할 경우, 도메인 패키지에서 함께 관리한다.

 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
