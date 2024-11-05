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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

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

    /*
    *   getPosts
    *   @param: 페이지번호, 페이지 크기
    *   @return: ResponseDto - 성공 메시지와 페이징된 게시글 목록을 포함
    */
    public ResponseDto<PagedResponseDto<PostResponseDto>> getPosts(int page, int size) {
        PagedResponseDto<PostResponseDto> pagedResponse = null;

        try {
        // page(와) size 값을 사용해 PageRequest 객체를 생성
        // : 해당 객체를 통해 DB에 해당 페이지의 Post 목록을 조회
        // : 결과는 Page<Post> 타입으로 반환

        /*
            1. PageRequest: Pageable 인터페이스의 구현체
                        - 특정 페이지의 데이터 조회 요청을 정의하는 객체
                        - 페이지 번호와 크기(데이터 수)를 기반으로 페이징 요청을 설정

                EX) PageRequest.of(int page, int size)
                    : page - 페이지 번호(0부터 시작)
                    : size - 한 페이지에 포함할 데이터의 개수
                    PageRequest.of(2, 10) - 3번째 페이지에 10개의 데이터

            2. Page<T>: JPA(에서) 제공하는 인터페이스, 특정 페이지에 대한 데이터와 페이징 정보를 포한한 객체
                - 조회된 데이터 목록뿐만 아니라 페이징과 관련된 메타정보도 함께 제공
                - 주요 메서드 -
                    : getContent() - 현재 페이지 데이터 목록
                    : getNumber() - 현재 페이지 번호 반환(0부터 시작)
                    : getSize() - 한 페이지에 포홤된 데이터의 개수 반환
                    : getTotalPages(): 전체 페이지수 반환
                    : getTotalElements(): 전체 데이터수 반환
        */
        Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size));

        List<PostResponseDto> postDtos = postPage.getContent().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

            pagedResponse = new PagedResponseDto<>(
                postDtos,
                postPage.getNumber(), // 요청된 페이지 번호
                postPage.getTotalPages(), // 전체 페이지 수
                postPage.getTotalElements() // 전체 요소 수
        );
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
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
