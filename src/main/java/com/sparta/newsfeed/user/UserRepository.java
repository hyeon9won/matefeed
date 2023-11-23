package com.sparta.newsfeed.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // 인터페이스로 작성해서 JpaRepository(스프링프레임워크)를 상속받아서 만들도록 한다.
    Optional<User> findByUsername(String username);

}
