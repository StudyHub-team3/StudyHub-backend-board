package com.example.StudyHub_backend_bord.common.dto;

import com.example.StudyHub_backend_bord.common.web.context.GatewayRequestHeaderUtils;
import com.example.StudyHub_backend_bord.domain.entity.Post;
import com.example.StudyHub_backend_bord.domain.entity.PostComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentCreateDto {
    @NotNull(message = "포스트 ID를 입력하세요.")
    private Long postId;
    @NotBlank(message = "댓글을 입력하세요.")
    private String comment;
    public PostComment toEntity(Post post) { //양뱡향
        PostComment postComment = new PostComment();
        postComment.setUserId(GatewayRequestHeaderUtils.getUserIdOrThrowException());
        postComment.setComment(this.comment);
        postComment.setPost(post);
        return postComment;
    }
}