package org.example.springbootdeveloper.controller;

import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.response.PagedResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.POST)
public class PostController {

    @Autowired
    private PostService postService;

    // CRUD 기능 명시
    // 1. 게시물 생성
    @PostMapping
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    // 2. 게시글 전체 조회
//    @GetMapping
//    public ResponseDto<List<PostResponseDto>> getAllPosts() {
//        return postService.getAllPosts();
//    }

    @GetMapping
    public ResponseEntity<ResponseDto<PagedResponseDto<PostResponseDto>>> getPosts(
            @RequestParam int page,
            @RequestParam int size) {

        ResponseDto<PagedResponseDto<PostResponseDto>> result = postService.getPosts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 3. 게시글 단건 조회
    @GetMapping("/{postId}")
    public ResponseDto<PostResponseDto> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 4. 특정 작성자 게시물 조회
    @GetMapping("/author")
    public ResponseDto<List<PostResponseDto>> getPostsByAuthor(@RequestParam String author) {
        return postService.getPostsByAuthor(author);
    }

    // 5. 게시글 수정
    @PutMapping("/{postId}")
    public ResponseDto<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
    }

    // 6. 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseDto<Void> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }
}
