package com.example.StudyHub_backend_bord.domain.repository;

import com.example.StudyHub_backend_bord.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 특정 유저가 작성한 게시글 1개 가져오기 (단건)
    Post findByUserId(String userId);

    // 최신순 정렬 (관리자용)
    List<Post> findAllByOrderByCreatedAtDesc();

    // 삭제되지 않은 게시글만 최신순으로 조회
    List<Post> findByIsDeletedFalseOrderByCreatedAtDesc();

    // studyId로 필터링하여 삭제되지 않은 게시글을 최신순으로 정렬
    List<Post> findByStudyIdAndIsDeletedFalseOrderByCreatedAtDesc(Long studyId);
}
