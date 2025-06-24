package com.example.StudyHub_backend_bord.api.open;

import com.example.StudyHub_backend_bord.common.dto.*;
import com.example.StudyHub_backend_bord.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO 글 목록.조회
@Slf4j
@RestController
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 작성 기능
    @PostMapping(value = "/post")
    public ApiResponseDto<String> createPost(@RequestBody @Valid PostCreateDto dto) {
        postService.createPost(dto);
        return ApiResponseDto.defaultOk();
    }

    // 댓글 추가
    @PostMapping(value = "/post/comment")
    public ApiResponseDto<String> addComment(@RequestBody @Valid PostCommentCreateDto dto) {
        postService.addPostComment(dto);
        return ApiResponseDto.defaultOk();
    }

    //게시글 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        return ResponseEntity.ok(postService.getPostList());

    }

    //게시글 상세 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    //게시글 수정
    @PutMapping("/post/{postId}")
    public ApiResponseDto<String> updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateDto dto) {
        postService.updatePost(postId, dto);
        return ApiResponseDto.defaultOk();
    }

    //게시글 삭제
    @DeleteMapping("/post/{postId}")
    public ApiResponseDto<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponseDto.defaultOk();
    }
    @PostMapping("/boards/{boardId}/posts")
    public ApiResponseDto<String> createBoardPost(
            @PathVariable Long boardId,
            @RequestBody @Valid PostCreateDto dto) {

        dto.setBoardId(boardId);  //  여기서 반드시 넣어줘야 함!
        postService.createPost(dto);
        return ApiResponseDto.defaultOk();
    }
}