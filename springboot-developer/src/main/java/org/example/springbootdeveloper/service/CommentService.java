package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.dto.request.CommentRequestDto;
import org.example.springbootdeveloper.dto.response.CommentResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Comment;
import org.example.springbootdeveloper.entity.Post;
import org.example.springbootdeveloper.repository.CommentRepository;
import org.example.springbootdeveloper.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 1. 새로운 댓글 등록
    public ResponseDto<CommentResponseDto> createComment(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다" + commentRequestDto.getPostId()));

        Comment comment = Comment.builder()
                .post(post)
                .content(commentRequestDto.getContent())
                .commenter(commentRequestDto.getCommenter())
                .build();
        Comment saveComment = commentRepository.save(comment);

        return ResponseDto.set(true, "success", convertResponseDto(saveComment));
    }
    
    // 2. 특정 게시물 댓글 조회
    public ResponseDto<List<CommentResponseDto>> getCommetsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return ResponseDto.set(true, "success", comments.stream().map(this::convertResponseDto).collect(Collectors.toList()));
    }

    // 3. 특정 ID 댓글 수정
    public ResponseDto<CommentResponseDto> updateComment(Long commentId, String newContent) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

            comment.setContent(newContent);

            Comment updateComment = commentRepository.save(comment);
            return ResponseDto.set(true, "success", convertResponseDto(updateComment));
    }

    // 4. 특정 ID 댓글 삭제
    public ResponseDto<Void> deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return null;
    }

    private CommentResponseDto convertResponseDto(Comment comment) {
        Long postId = comment.getPost().getId();

        return new CommentResponseDto(
                comment.getId(),
                postId,
                comment.getContent(),
                comment.getCommenter()
        );
    }
}


