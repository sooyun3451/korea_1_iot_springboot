package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // User 타입의 엔티티 "필드를 선택적으로 가져올 수 있는" 타입 정의(로그인)
    Optional<User> findByEmail(String email);

    // userRequestDto(안에) userId로 받아온다면 findByUserId(라고) 이름지어서 매개변수도 userId 넣기
    // Optional<User> findByUserid(String userId);

    // 해당 엔티티에 전달하는 email 이 존재할 경우 true, 존재하지 않을 경우 false 반환(회원가입)
    boolean existsByEmail(String email);
}
