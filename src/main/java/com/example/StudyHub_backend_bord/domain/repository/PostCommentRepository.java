package com.example.StudyHub_backend_bord.domain.repository;

import com.example.StudyHub_backend_bord.domain.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    PostComment findByUserId(String userId);
}