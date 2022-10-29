package com.carepay.assignment.repository;

import com.carepay.assignment.domain.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    Page<PostComment> findByPostId(Long postId, Pageable pageable);
    Optional<PostComment> findByPostIdAndId(Long postId, Long id);
    boolean existsByPostIdAndId(Long id, Long postId);
}
