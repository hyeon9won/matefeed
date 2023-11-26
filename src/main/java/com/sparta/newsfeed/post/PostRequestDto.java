package com.sparta.newsfeed.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String team;
    private String title;
    private String username;
    private String password;
    private String content;
}
