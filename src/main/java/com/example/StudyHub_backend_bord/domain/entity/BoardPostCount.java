package com.example.StudyHub_backend_bord.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BoardPostCount {

    @Id
    private Long boardId; // 게시판 ID (Post의 boardId와 일치)

    private Long postCount;

    public static BoardPostCount init(Long boardId, Long count) {
        BoardPostCount c = new BoardPostCount();
        c.boardId = boardId;
        c.postCount = count;
        return c;
    }

    public void increase() {
        postCount++;
    }

    public void decrease() {
        postCount = Math.max(0, postCount - 1);
    }
}
