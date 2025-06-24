package com.example.StudyHub_backend_bord.service;

import com.example.StudyHub_backend_bord.common.exception.NotFound;
import com.example.StudyHub_backend_bord.common.web.context.GatewayRequestHeaderUtils;
import com.example.StudyHub_backend_bord.domain.entity.Post;
import com.example.StudyHub_backend_bord.domain.entity.PostLike;
import com.example.StudyHub_backend_bord.domain.repository.PostLikeRepository;
import com.example.StudyHub_backend_bord.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글에 좋아요 토글 (추가/취소)
    @Transactional
    public void togglePostLike(Long postId) {
        String userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();  // 현재 유저의 ID

        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFound("게시글을 찾을 수 없습니다."));

        // 이미 좋아요를 눌렀는지 확인
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            // 좋아요 취소
            postLikeRepository.deleteByPostIdAndUserId(postId, userId);
            log.info("좋아요 취소: postId = {}, userId = {}", postId, userId);
        } else {
            // 좋아요 추가
            PostLike postLike = new PostLike();
            postLike.setPost(post);

            // userId만 설정 (User 엔티티 없이 처리)
            postLike.setUserId(userId);  // userId만 설정, User 엔티티를 조회하지 않음

            postLikeRepository.save(postLike);  // 좋아요 저장

            log.info("좋아요 추가: postId = {}, userId = {}", postId, userId);
        }
    }

    // 게시글 좋아요 개수 조회
    @Transactional(readOnly = true)
    public long getPostLikeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}
