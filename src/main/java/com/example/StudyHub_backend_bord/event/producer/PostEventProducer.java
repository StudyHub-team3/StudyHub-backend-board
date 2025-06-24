package com.example.StudyHub_backend_bord.event.producer;

import com.example.StudyHub_backend_bord.event.dto.PostCreateEvent;
import com.example.StudyHub_backend_bord.event.dto.PostDeleteEvent;
import com.example.StudyHub_backend_bord.event.dto.PostUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class PostEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;  // Object로 설정 (제네릭 다양성 위해)

    public void sendCreateEvent(PostCreateEvent eventData) {
        KafkaEvent<PostCreateEvent> event = new KafkaEvent<>(
                "POST_CREATED",
                eventData,
                Instant.now().toString()
        );
        kafkaTemplate.send("post-events", event);
    }

    public void sendUpdateEvent(PostUpdateEvent eventData) {
        KafkaEvent<PostUpdateEvent> event = new KafkaEvent<>(
                "POST_UPDATED",
                eventData,
                Instant.now().toString()
        );
        kafkaTemplate.send("post-events", event);
    }

    public void sendDeleteEvent(PostDeleteEvent eventData) {
        KafkaEvent<PostDeleteEvent> event = new KafkaEvent<>(
                "POST_DELETED",
                eventData,
                Instant.now().toString()
        );
        kafkaTemplate.send("post-events", event);
    }
}