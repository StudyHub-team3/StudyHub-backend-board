package com.example.StudyHub_backend_bord.api.backend;


import com.example.StudyHub_backend_bord.common.dto.ApiResponseDto;
import com.example.StudyHub_backend_bord.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/study-post/v1")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 좋아요 토글 (추가/취소)
    @PutMapping("/studies/{studyId}/post/{postId}/like")
    public ApiResponseDto<String> toggleLike(
            @PathVariable Long studyId,  // 스터디 ID
            @PathVariable Long postId) {  // 게시글 ID

        try {
            postLikeService.togglePostLike(postId);  // 좋아요 추가/취소 메서드 호출
            return ApiResponseDto.defaultOk();  // 성공적인 응답 반환
        } catch (Exception e) {
            log.error("좋아요 처리 중 오류 발생", e);
            // 오류 발생 시 에러 응답 반환 (String 타입으로 응답)
            return ApiResponseDto.createError("500", "서버 오류 발생");
        }
    }

    // 게시글 좋아요 수 조회
    @GetMapping("/studies/{studyId}/post/{postId}/likes")
    public ApiResponseDto<Long> getPostLikeCount(
            @PathVariable Long studyId,
            @PathVariable Long postId) {
        try {
            long likeCount = postLikeService.getPostLikeCount(postId);  // 좋아요 수 조회
            return ApiResponseDto.createOk(likeCount);  // 좋아요 개수 반환 (Long 타입)
        } catch (Exception e) {
            log.error("좋아요 수 조회 중 오류 발생", e);
            // 오류 발생 시 에러 응답 반환 (Long 타입으로 응답)
            return ApiResponseDto.createError("500", "서버 오류 발생", 0L);  // 기본값으로 0L을 반환
        }
    }
}

