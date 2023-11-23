package com.sparta.newsfeed.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {
    private Long id;
    private String username;
    private String password;
    private String newPassword;
    private String team;
}
