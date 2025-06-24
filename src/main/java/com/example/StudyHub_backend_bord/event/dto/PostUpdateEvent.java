package com.example.StudyHub_backend_bord.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateEvent {
    private Long postId;
    private String newTitle;
    private String editor;
}