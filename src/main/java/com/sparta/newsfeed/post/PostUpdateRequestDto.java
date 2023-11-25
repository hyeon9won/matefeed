package com.sparta.newsfeed.post;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private Long id;
    private String title;
    private String team;
    private String username;
    private String password;
    private String contents;
}
