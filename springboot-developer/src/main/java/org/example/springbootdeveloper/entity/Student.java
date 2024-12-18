package org.example.springbootdeveloper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // JPA Entity 임을 선언, DB 테이블과 매핑
@Table(name = "students") // 해당 Entity 가 'students' 라는 이름의 테이블과 매핑됨을 명시 +) 테이블명과 Entity 의 이름이 동일한 경우 (name= "") 작성 하지 않아도 된다.
@Getter
@Setter
public class Student {

    @Id // 해당 필드가 테이블의 키본 키(PK)임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 자동 생성 - DB의 auto_inclement 를 사용
    private Long id;
    private String name;
    private String email;

    protected Student() {}
        // JPA는 엔터티 생성 시 기본 생성자를 사용

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
