package com.example.StudyHub_backend_bord.common.dto;

import com.example.StudyHub_backend_bord.common.web.context.GatewayRequestHeaderUtils;
import com.example.StudyHub_backend_bord.domain.entity.Post;
import com.example.StudyHub_backend_bord.type.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class PostCreateDto {
    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;

    @NotBlank(message = "본문을 입력하세요.")
    private String content;

    @NotNull(message = "게시글 타입을 선택하세요.")  // 타입선택
    private PostType type;

    private Long studyId;

    public Post toEntity() {
        Post post = new Post();
        post.setUserId(GatewayRequestHeaderUtils.getUserIdOrThrowException());
//        String userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();
//        log.info("PostCreateDto.toEntity() - userId: {}", userId);  // ← 여기서 오류가 난 건지 확인
//
//        Post post = new Post();
//        post.setUserId(userId);

        post.setPost(this.title, this.content);
        post.setType(this.type);

        post.setStudyId(this.studyId);

        // boardType 설정 (studyId가 있을 경우 "study"로 설정, 없으면 "main")
        if (this.studyId != null) {
            post.setBoardType("study"); // 스터디 게시판
        } else {
            post.setBoardType("main"); // 메인 게시판
        }

        return post;
    }
}
