package com.example.StudyHub_backend_bord.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateEvent {
    private Long postId;
    private String title;
    private String writer;
}
