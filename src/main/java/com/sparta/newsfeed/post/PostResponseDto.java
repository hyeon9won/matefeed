package com.sparta.newsfeed.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String team;
    private String title;
    private String username;
    private String contents;
    LocalDateTime createdAt;

    public PostResponseDto(PostEntity savePost) {
        this.id = savePost.getId();
        this.team = savePost.getTeam();
        this.title = savePost.getTitle();
        this.username = savePost.getUsername();
        this.contents = savePost.getContents();
        this.createdAt = savePost.getCreatedAt();
    }
}

