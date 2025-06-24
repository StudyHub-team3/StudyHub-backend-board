package com.example.StudyHub_backend_bord.event.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaEvent<T> {
    private String eventType;
    private T data;
    private String timestamp;
}