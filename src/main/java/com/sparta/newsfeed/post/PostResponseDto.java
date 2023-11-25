package com.sparta.newsfeed.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    LocalDateTime createdAt;

    public PostResponseDto(PostEntity savePost) {
        this.id = savePost.getId();
        this.title = savePost.getTitle();
        this.author = savePost.getAuthor();
        this.content = savePost.getContents();
        this.createdAt = savePost.getCreatedAt();
    }
}

