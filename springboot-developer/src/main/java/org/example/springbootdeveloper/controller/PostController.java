package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.response.PagedResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.POST)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    // CRUD 기능 명시
    // 1. 게시물 생성
    @PostMapping
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    // 2. 게시글 전체 조회
    // @GetMapping
    // public ResponseDto<List<PostResponseDto>> getAllPosts() {
    //    return postService.getAllPosts();
    // }

    @GetMapping
    public ResponseEntity<ResponseDto<PagedResponseDto<PostResponseDto>>> getPosts(@RequestParam int page, @RequestParam int size) {
            // @RequestParam: url(을) 통해 key(와) value 형태로 요청값을 보냄
            // page: 현재 페이지 번호, size: 페이지 당 표시할 데이터 개수
        try {
        // 요청한 페이지에 대한 데이터를 반환(응답)
        ResponseDto<PagedResponseDto<PostResponseDto>> result = postService.getPosts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
