package com.example.StudyHub_backend_bord.domain.repository;

import com.example.StudyHub_backend_bord.domain.entity.BoardPostCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardPostCountRepository extends JpaRepository<BoardPostCount, Long> {

    // 게시글 수 증가 (UPDATE 후 성공 여부 반환)
    @Modifying
    @Query("UPDATE BoardPostCount b SET b.postCount = b.postCount + 1 WHERE b.boardId = :boardId")
    int increase(@Param("boardId") Long boardId);

    // 게시글 수 감소 (선택사항)
    @Modifying
    @Query("UPDATE BoardPostCount c SET c.postCount = c.postCount - 1 WHERE c.boardId = :boardId")
    int decrease(@Param("boardId") Long boardId);
}