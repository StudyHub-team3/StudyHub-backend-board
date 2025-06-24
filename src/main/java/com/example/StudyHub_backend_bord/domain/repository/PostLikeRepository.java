package com.example.StudyHub_backend_bord.domain.repository;

import com.example.StudyHub_backend_bord.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 특정 게시글에 특정 유저가 좋아요를 눌렀는지 확인
    boolean existsByPostIdAndUserId(Long postId, String userId);

    // 특정 게시글에 대해 특정 유저의 좋아요 삭제
    void deleteByPostIdAndUserId(Long postId, String userId);

    // 특정 게시글의 좋아요 수 조회
    long countByPostId(Long postId);  // 특정 게시글에 달린 좋아요 수 카운트
}
