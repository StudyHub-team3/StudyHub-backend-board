package com.example.StudyHub_backend_bord.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDeleteEvent {
    private Long postId;
    private String deleter;
}