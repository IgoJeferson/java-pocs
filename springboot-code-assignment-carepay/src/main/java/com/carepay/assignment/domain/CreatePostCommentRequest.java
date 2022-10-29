package com.carepay.assignment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CreatePostCommentRequest {

    @NotNull(message = "Post cannot be null")
    private Long postId;

    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 160, message = "Comment should not be greater than 160 characters")
    private String comment;

}