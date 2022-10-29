package com.carepay.assignment.web;

import com.carepay.assignment.domain.CreatePostCommentRequest;
import com.carepay.assignment.domain.PostCommentDetails;
import com.carepay.assignment.service.PostCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostCommentController {
    private final PostCommentService postCommentService;

    public PostCommentController(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @GetMapping
    Page<PostCommentDetails> getPostComments(@PathVariable (value = "postId") Long postId, Pageable pageable) {
        return postCommentService.getPostComments(postId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostCommentDetails createPostComment(@PathVariable (value = "postId") Long postId, @RequestBody CreatePostCommentRequest createPostCommentRequest) {
        createPostCommentRequest.setPostId(postId);
        return postCommentService.createPostComment(createPostCommentRequest);
    }

    @GetMapping("{id}")
    PostCommentDetails getPostComment(@PathVariable (value = "postId") Long postId, @PathVariable("id") final Long id) {
        return postCommentService.getPostCommentDetails(postId, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePostComment(@PathVariable (value = "postId") Long postId, @PathVariable("id") final Long id) {
        postCommentService.deletePostComment(postId, id);
    }
}
