package com.example.StudyHub_backend_bord.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "post_like",
        indexes = {
                @Index(columnList = "user_id"),
                @Index(columnList = "post_id")
        }
)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // 좋아요 ID (기본키)

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post.class)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;  // 좋아요가 눌린 게시글

    @Column(name = "user_id", nullable = false)
    private String userId;  // 좋아요를 누른 유저 ID (User 엔티티 없이 처리)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시각
}
