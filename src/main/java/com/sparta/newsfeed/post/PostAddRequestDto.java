package com.sparta.newsfeed.post;

import lombok.Getter;

@Getter
public class PostAddRequestDto {
    private Long id;
    private String team;
    private String title;
    private String username;
    private String password;
    private String content;
}

