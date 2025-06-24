package com.example.StudyHub_backend_bord.event.dto;

import com.example.StudyHub_backend_bord.domain.entity.PostComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Kafka로 전송할 댓글 이벤트 메시지 DTO 클래스
//댓글 생성/수정/삭제 등의 이벤트를 담아 Kafka에 전송하는 데 사용됩니다.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentEvent {

    // Kafka에서 사용할 토픽 이름 (소비자와 생산자가 이 이름을 기준으로 통신)
    public static final String Topic = "post-comment-topic";

    // 이벤트의 동작 유형 (예: "Create", "Update", "Delete")
    private String action;

    // 댓글 ID (고유 식별자)
    private Long commentId;

    // 댓글 내용
    private String comment;

    // 댓글 작성자의 사용자 ID
    private String userId;

//  PostComment 엔티티를 기반으로 이벤트 메시지를 생성하는 팩토리 메서드
//  @param action "Create", "Update", "Delete" 등의 동작을 명시
//  @param entity PostComment 엔티티 객체
//  @return PostCommentEvent 객체

    public static PostCommentEvent fromEntity(String action, PostComment entity) {
        return new PostCommentEvent(
                action,
                entity.getId(),
                entity.getComment(),
                entity.getUserId()
        );
    }
}