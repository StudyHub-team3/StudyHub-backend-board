package com.example.StudyHub_backend_bord.api.open;

import com.example.StudyHub_backend_bord.common.dto.ApiResponseDto;
import com.example.StudyHub_backend_bord.common.dto.PostCreateDto;
import com.example.StudyHub_backend_bord.common.dto.PostResponseDto;
import com.example.StudyHub_backend_bord.common.dto.PostUpdateDto;
import com.example.StudyHub_backend_bord.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudyPostController {

    private final PostService postService;

    // 스터디별 게시글 작성
    @PostMapping("/studies/{studyId}/posts")
    public ApiResponseDto<String> createStudyPost(
            @PathVariable Long studyId,
            @RequestBody @Valid PostCreateDto dto) {

        if (studyId == null) {
            throw new IllegalArgumentException("스터디 게시판 글 작성 시 studyId는 필수입니다.!!");
        }
//            @PathVariable Long studyId,
//            @RequestBody @Valid PostCreateDto dto,
//            @RequestHeader(value = "X-USER-ID", required = false) String userId) {
//
//        log.info("studyId = {}", studyId);
//        log.info("userId (from header) = {}", userId);
//        log.info("dto = {}", dto);

        dto.setStudyId(studyId);
        postService.createPost(dto);
        return ApiResponseDto.defaultOk();
    }
    //게시글 목록 조회
    @GetMapping("/studies/{studyId}/posts")
    public ResponseEntity<List<PostResponseDto>> getPostListByStudyId(@PathVariable Long studyId) {
        List<PostResponseDto> postList = postService.getPostListByStudyId(studyId);
        return ResponseEntity.ok(postList);
    }

    //게시글 상세조회
    @GetMapping("/studies/{studyId}/posts/{postId}")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable Long studyId, @PathVariable Long postId) {
        PostResponseDto post = postService.getPostDetail(postId);
        return ResponseEntity.ok(post);
    }
    // 게시글 수정
    @PutMapping("/studies/{studyId}/posts/{postId}")
    public ApiResponseDto<String> updatePost(
            @PathVariable Long studyId,
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateDto dto) {

        postService.updatePost(postId, dto);
        return ApiResponseDto.defaultOk();
    }

    // 게시글 삭제
    @DeleteMapping("/studies/{studyId}/posts/{postId}")
    public ApiResponseDto<String> deletePost(
            @PathVariable Long studyId,
            @PathVariable Long postId) {

        postService.deletePost(postId);
        return ApiResponseDto.defaultOk();
    }
    //좋아요 기능 오류나서 빼버림
}