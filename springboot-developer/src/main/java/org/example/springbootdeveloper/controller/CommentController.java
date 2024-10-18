package org.example.springbootdeveloper.controller;

import org.example.springbootdeveloper.dto.request.CommentRequestDto;
import org.example.springbootdeveloper.dto.response.CommentResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    // 의존성 주입(DI)
    // - 필드 주입, 메서드 주입, 생성자 주입

    // * @RequiredArgsConstructor: 생성자 주입 방식 - 롬복 어노테이션(final 과 @Nonnull 필드에 대해 자동으로 생성자를 생성)
    // @Autowired: 필드 주입 방법 - 스프링이 필드 자체에 객체를 주입

    @Autowired
    private CommentService commentService;

    // CRUD 기능 명시
    // 1. 댓글 생성
    @PostMapping
    public ResponseDto<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createComment(commentRequestDto);
    }

    // 2. 전체 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseDto<List<CommentResponseDto>> getCommetsByPost(@PathVariable Long postId) {
        return commentService.getCommetsByPost(postId);
    }

    // 3. 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseDto<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody String newContent) {
        return commentService.updateComment(commentId, newContent);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseDto<Void> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
