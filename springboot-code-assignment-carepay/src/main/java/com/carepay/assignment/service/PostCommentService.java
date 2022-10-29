package com.carepay.assignment.service;

import com.carepay.assignment.domain.CreatePostCommentRequest;
import com.carepay.assignment.domain.PostCommentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface PostCommentService {
    PostCommentDetails createPostComment(@Valid CreatePostCommentRequest createPostCommentRequest);

    Page<PostCommentDetails> getPostComments(Long postId, final Pageable pageable);

    PostCommentDetails getPostCommentDetails(Long postId, Long id);

    void deletePostComment(Long postId, Long id);
}
