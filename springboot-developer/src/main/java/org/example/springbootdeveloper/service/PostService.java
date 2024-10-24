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
        try {
            Post post = Post.builder()
                    .title(postRequestDto.getTitle())
                    .content(postRequestDto.getContent())
                    .author(postRequestDto.getAuthor())
                    .build();

            Post savePost = postRepository.save(post);

                // PostResponseDto postResponseDto = new PostResponseDto(
                // savePost.getId(),
                // savePost.getTitle(),
                // savePost.getContent(),
                // savePost.getAuthor(),
                // new ArrayList<>()
                // );

            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .id(savePost.getId())
                    .title(savePost.getTitle())
                    .content(savePost.getContent())
                    .author(savePost.getAuthor())
                    .build();

            return ResponseDto.setSuccess("success", postResponseDto);
        } catch (Exception e) {
            return ResponseDto.setFailed(e.getMessage());
        }
    }

    // 2. 모든 게시물 찾기
    public ResponseDto<List<PostResponseDto>> getAllPosts() {
        try {
            return ResponseDto.setSuccess("success", postRepository.findAll().stream()
                    .map(this::convertResponseDto)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseDto.setFailed(e.getMessage());
        }
    }

    // 3. 특정 ID 게시물 찾기
    public ResponseDto<PostResponseDto> getPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));
            return ResponseDto.setSuccess("success", convertResponseDto(post));
        } catch (Exception e) {
            return ResponseDto.setFailed(e.getMessage());
        }
    }

    // 4. 특정 작성자 게시물 조히
    public ResponseDto<List<PostResponseDto>> getPostsByAuthor(String author) {
        try {
            return ResponseDto.setSuccess("success", postRepository.findByAuthor(author).stream()
                    .map(this::convertResponseDto).collect(Collectors.toList()));
        } catch(Exception e) {
            return ResponseDto.setFailed(e.getMessage());
        }
    }

    // 5. 특정 ID 게시물 수정
    public ResponseDto<PostResponseDto> updatePost(Long postId, PostRequestDto postRequestDto) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));

            post.setTitle(postRequestDto.getTitle());
            post.setContent(postRequestDto.getContent());
            post.setAuthor(postRequestDto.getAuthor());

            Post updatePost = postRepository.save(post);

            return ResponseDto.setSuccess("success", convertResponseDto(updatePost));
        } catch (Exception e) {
            return ResponseDto.setFailed(e.getMessage());
        }
    }

    // 6. 특정 ID 게시물 삭제
    public ResponseDto<Void> deletePost(Long postId) {
        try {

            Post post = postRepository.findById(postId)
                            .orElseThrow(() -> new Error("post not found with id" + postId));
            postRepository.delete(post);

            return ResponseDto.setSuccess("success", null);
        } catch (Exception e) {
            return ResponseDto.setFailed(e.getMessage());
        }
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
