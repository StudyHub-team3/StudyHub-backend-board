package com.example.StudyHub_backend_bord.event.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

//TODO 댓글 등록 시 알림용 Kafka 발행
@Service
public class PostEventProducer {

    private static final String POST_EVENTS_TOPIC = "post-events"; // Kafka 토픽명

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 게시글 생성 이벤트 발행
    public void sendPostCreatedEvent(String postId, String title, String content, String authorId) {
        String eventMessage = String.format("{\"event\":\"postCreated\", \"postId\":\"%s\", \"title\":\"%s\", \"content\":\"%s\", \"authorId\":\"%s\"}", postId, title, content, authorId);
        sendMessage(eventMessage);
    }

    // 게시글 수정 이벤트 발행
    public void sendPostUpdatedEvent(String postId, String title, String content) {
        String eventMessage = String.format("{\"event\":\"postUpdated\", \"postId\":\"%s\", \"title\":\"%s\", \"content\":\"%s\"}", postId, title, content);
        sendMessage(eventMessage);
    }

    // 게시글 삭제 이벤트 발행
    public void sendPostDeletedEvent(String postId) {
        String eventMessage = String.format("{\"event\":\"postDeleted\", \"postId\":\"%s\"}", postId);
        sendMessage(eventMessage);
    }

    // 게시글 좋아요 이벤트 발행
    public void sendPostLikedEvent(String postId, String userId) {
        String eventMessage = String.format("{\"event\":\"postLiked\", \"postId\":\"%s\", \"userId\":\"%s\"}", postId, userId);
        sendMessage(eventMessage);
    }

    // 공통 메시지 전송 로직
    private void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(POST_EVENTS_TOPIC, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Message sent successfully: " + result.getProducerRecord());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.err.println("Message sending failed: " + ex.getMessage());
            }
        });
    }
}