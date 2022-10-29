package com.carepay.assignment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDetails extends PostInfo {
    private final String content;

    @Builder
    public PostDetails(Long id, String title, String content) {
        super(id, title);
        this.content = content;
    }
}
