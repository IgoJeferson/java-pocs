package com.carepay.assignment.domain;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
public class CreatePostRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 128, message = "Title should not be greater than 128 characters")
    private String title;

    @NotBlank(message = "Content cannot be null")
    @Size(max = 128, message = "Content should not be greater than 128 characters")
    private String content;

}
