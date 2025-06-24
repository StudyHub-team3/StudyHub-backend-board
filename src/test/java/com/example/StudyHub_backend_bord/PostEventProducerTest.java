package com.example.StudyHub_backend_bord;


import com.example.StudyHub_backend_bord.event.dto.PostCreateEvent;
import com.example.StudyHub_backend_bord.event.producer.KafkaEvent;
import com.example.StudyHub_backend_bord.event.producer.PostEventProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PostEventProducerTest {

    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    PostEventProducer producer;

    @Test
    void sendCreateEvent_shouldCallKafkaTemplate() {
        PostCreateEvent event = new PostCreateEvent(1L, "제목", "작성자");

        producer.sendCreateEvent(event);

        verify(kafkaTemplate).send(anyString(), any(KafkaEvent.class));
    }
}
