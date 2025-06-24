package com.example.StudyHub_backend_bord.domain.entity;

import com.example.StudyHub_backend_bord.type.PostType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//이게 찐 파일임.
// 게시글 Entity
@Slf4j
@Entity
@Table(
        name = "post",
        indexes = {
                @Index(columnList = "user_id"),
                @Index(columnList = "created_at"),
                @Index(columnList = "updated_at")
        }
)
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 게시글 ID (기본키)

    @Column(name = "title", nullable = false)
    private String title; // 게시글 제목

    @Column(name = "content", nullable = false)
    private String content; // 게시글 내용

    @Getter @Setter
    @Column(name = "user_id", nullable = false)
    private String userId; // 작성자 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostType type; // 게시글 유형 (Enum)

    // 삭제
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시각

    @Column(name = "updated_at")
    private LocalDateTime updatedDatetime; // 수정 시각

    @Column(name = "study_id") //스터디게시판 안에서는 반드시 필수로 해줘야함. CustomValidator를 이용하기!
    private Long studyId;

    // 게시판 종류 (study/main)
    @Column(name = "board_type", nullable = false)
    private String boardType;

    @Column(name = "board_id", nullable = false)
    private Long boardId; // 게시판 ID


    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>(); // 게시글에 달린 댓글 목록
    // 제목, 내용 수정 시 사용
    public void setPost(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedDatetime = LocalDateTime.now(); // 수정일 갱신
    }

    // 댓글 추가
    public void addComment(PostComment comment) {
        comment.setPost(this); // 연관관계 설정
        this.comments.add(comment);
    }
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}