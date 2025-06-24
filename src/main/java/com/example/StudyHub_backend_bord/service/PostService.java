package com.example.StudyHub_backend_bord.service;

import com.example.StudyHub_backend_bord.common.dto.PostCommentCreateDto;
import com.example.StudyHub_backend_bord.common.dto.PostCreateDto;
import com.example.StudyHub_backend_bord.common.dto.PostResponseDto;
import com.example.StudyHub_backend_bord.common.dto.PostUpdateDto;
import com.example.StudyHub_backend_bord.common.exception.NotFound;
import com.example.StudyHub_backend_bord.common.web.context.GatewayRequestHeaderUtils;
import com.example.StudyHub_backend_bord.domain.entity.BoardPostCount;
import com.example.StudyHub_backend_bord.domain.entity.Post;
import com.example.StudyHub_backend_bord.domain.entity.PostComment;
import com.example.StudyHub_backend_bord.domain.repository.BoardPostCountRepository;
import com.example.StudyHub_backend_bord.domain.repository.PostCommentRepository;
import com.example.StudyHub_backend_bord.domain.repository.PostRepository;
import com.example.StudyHub_backend_bord.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final BoardPostCountRepository boardPostCountRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    // 게시글 생성
    @Transactional
    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        postRepository.save(post);
//        log.info(" PostService.createPost() 진입"); // 무조건 찍혀야 함
//
//        Post post = createDto.toEntity();
//        log.info(" Post 엔티티 생성: {}", post);
//
//        postRepository.save(post);
//        log.info(" 저장 완료");
        // 게시글 수 증가 시도
        int result = boardPostCountRepository.increase(createDto.getBoardId());

        // 만약 증가 실패하면 (게시판 글 수가 없던 경우)
        if (result == 0) {
            boardPostCountRepository.save(
                    BoardPostCount.init(createDto.getBoardId(), 1L)
            );
        }
    }

    // 댓글 추가 (Kafka 이벤트 발행은 테스트 시 주석 가능)
    @Transactional
    public void addPostComment(PostCommentCreateDto createDto) {
        Post post = postRepository.findById(createDto.getPostId())
                .orElseThrow(() -> new NotFound("포스팅 글을 찾을 수 없습니다."));

        PostComment postComment = createDto.toEntity(post);
        postCommentRepository.save(postComment);
        post.addComment(postComment);

        // Kafka 주석 처리 중
//        kafkaMessageProducer.send(
//                PostCommentEvent.Topic,
//                PostCommentEvent.fromEntity("Create", postComment)
//        );
    }

    // 게시글 목록 조회 (소프트 삭제 제외 + 최신순 정렬)
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        return postRepository.findByIsDeletedFalseOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 스터디 게시글 목록 조회 (소프트 삭제 제외 + 최신순 정렬)
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostListByStudyId(Long studyId) {
        return postRepository.findByStudyIdAndIsDeletedFalseOrderByCreatedAtDesc(studyId)
                .stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
    //게시글 상세 조회..
    @Transactional(readOnly = true)
    public PostResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFound("해당 게시글이 존재하지 않습니다."));

        if (post.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.GONE, "삭제된 게시글입니다.");
        }

        return PostResponseDto.fromEntity(post);
    }

    //  게시글 수정
    @Transactional
    public void updatePost(Long postId, PostUpdateDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFound("해당 게시글이 존재하지 않습니다."));

        String userIdFromHeader = GatewayRequestHeaderUtils.getUserIdOrThrowException();
        if (!post.getUserId().equals(userIdFromHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        post.setPost(dto.getTitle(), dto.getContent());
        post.setType(dto.getType());
    }

    // 게시글 삭제 (소프트 삭제 처리)
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFound("해당 게시글이 존재하지 않습니다."));

        String userIdFromHeader = GatewayRequestHeaderUtils.getUserIdOrThrowException();
        if (!post.getUserId().equals(userIdFromHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        post.setIsDeleted(true);
    }
}