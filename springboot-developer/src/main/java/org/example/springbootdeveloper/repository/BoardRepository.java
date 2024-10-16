package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
