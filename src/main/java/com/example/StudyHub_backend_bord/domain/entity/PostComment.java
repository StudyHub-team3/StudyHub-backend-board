package com.example.StudyHub_backend_bord.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(
        name = "post_comment",
        indexes = {
                @Index(columnList = "user_id"),
                @Index(columnList = "created_at"),
                @Index(columnList = "updated_at")
        }
)
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private Long id; // 댓글 ID (기본키)

    @Column(name = "comment", nullable = false)
    @Getter
    private String comment; // 댓글 내용

    // 댓글 수정 메서드
    public void setComment(String comment) {
        this.comment = comment;
        this.updatedDatetime = LocalDateTime.now(); // 수정일 갱신
    }

    @Column(name = "user_id", nullable = false)
    @Getter @Setter
    private String userId; // 댓글 작성자 ID

    @Column(name = "created_at", nullable = false)
    @Getter
    private LocalDateTime createdDatetime = LocalDateTime.now(); // 생성 시각

    @Column(name = "updated_at")
    @Getter
    private LocalDateTime updatedDatetime; // 수정 시각

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post.class)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private Post post; // 댓글이 달린 게시글 (연관관계 설정)

}