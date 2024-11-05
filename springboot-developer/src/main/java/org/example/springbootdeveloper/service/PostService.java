package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ResponseMessage;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.response.CommentResponseDto;
import org.example.springbootdeveloper.dto.response.PagedResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Post;
import org.example.springbootdeveloper.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public ResponseDto<PostResponseDto> createPost(PostRequestDto dto) {
        try {
            Post post = Post.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .author(dto.getAuthor())
                    .build();
            postRepository.save(post);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, convertToPostResponseDto(post));
        } catch (Exception e) {
            return ResponseDto.setFailed("게시글 등록 중 오류가 발생했습니다: " + e.getMessage());
        }

    }

    public ResponseDto<List<PostResponseDto>> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            List<PostResponseDto> postResponseDtos = posts.stream()
                    .map(this::convertToPostResponseDto)
                    .collect(Collectors.toList());

            if (postResponseDtos.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_POST);
            }

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, postResponseDtos);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

    }

    public ResponseDto<PagedResponseDto<PostResponseDto>> getPosts(int page, int size) {
        Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size));

        List<PostResponseDto> postDtos = postPage.getContent().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        PagedResponseDto<PostResponseDto> pagedResponse = new PagedResponseDto<>(
                postDtos,
                postPage.getNumber(),
                postPage.getTotalPages(),
                postPage.getTotalElements()
        );

        return ResponseDto.setSuccess("게시글 목록 조회 성공", pagedResponse);
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

    private PostResponseDto convertToPostResponseDto(Post post) {
        List<CommentResponseDto> commentDtos = post.getComments().stream()
                .map(comment -> new CommentResponseDto(comment.getId(), post.getId(), comment.getContent(), comment.getCommenter()))
                .collect(Collectors.toList());

        return new PostResponseDto(
                post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), commentDtos
        );
    }


}
