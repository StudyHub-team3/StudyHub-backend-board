package com.example.StudyHub_backend_bord.common.dto;

import com.example.StudyHub_backend_bord.type.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "본문을 입력하세요.")
    private String content;

    @NotNull(message = "게시글 타입을 선택하세요.")
    private PostType type;
}