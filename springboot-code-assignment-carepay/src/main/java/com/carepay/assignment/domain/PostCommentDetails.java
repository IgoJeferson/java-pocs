package com.carepay.assignment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCommentDetails {
    private Long id;
    private Long postId;
    private String comment;
}
