package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.response.CommentResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Post;
import org.example.springbootdeveloper.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 1. 게시물 생성
    public ResponseDto<PostResponseDto> createPost(PostRequestDto postRequestDto) {
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .author(postRequestDto.getAuthor())
                .build();

        Post savePost = postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                new ArrayList<>()
        );

        return ResponseDto.set(true, "success", postResponseDto);
    }

    // 2. 모든 게시물 찾기
    public ResponseDto<List<PostResponseDto>> getAllPosts() {
       return ResponseDto.set(true, "success", postRepository.findAll().stream()
               .map(this::convertResponseDto)
               .collect(Collectors.toList()));
    }

    // 3. 특정 ID 게시물 찾기
    public ResponseDto<PostResponseDto> getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));

        return ResponseDto.set(true, "success",convertResponseDto(post));
    }

    // 4. 특정 ID 게시물 수정
    public ResponseDto<PostResponseDto> updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setAuthor(postRequestDto.getAuthor());

        Post updatePost = postRepository.save(post);
        return ResponseDto.set(true, "success", convertResponseDto(post));
    }

    // 5. 특정 ID 게시물 삭제
    public ResponseDto<Void> deletePost(Long postId) {
        postRepository.deleteById(postId);
        return null;
    }

    public PostResponseDto convertResponseDto(Post post) {
        List<CommentResponseDto> commentResponseDtos = post.getComments().stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        post.getId(),
                        comment.getContent(),
                        comment.getCommenter()
                ))
                .toList();

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                commentResponseDtos
        );
    }
}
