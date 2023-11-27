package com.sparta.newsfeed.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {
    private Long id;
    private String username;
    private String password;
    private String checkPassword; // password와 일치하는지 확인
    private String team;
}
