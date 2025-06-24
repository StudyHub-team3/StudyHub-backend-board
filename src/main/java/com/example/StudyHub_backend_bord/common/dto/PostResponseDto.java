package com.example.StudyHub_backend_bord.common.dto;

import com.example.StudyHub_backend_bord.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String type;  // Enum(PostType) // 질문, 자유, 기록
    private LocalDateTime createdAt;

    // Entity → DTO로 변환
    public static PostResponseDto fromEntity(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType().name(),  //  Enum
                post.getCreatedAt()
        );
    }
}