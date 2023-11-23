package com.sparta.newsfeed.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
