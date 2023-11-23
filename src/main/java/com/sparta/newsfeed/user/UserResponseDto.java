package com.sparta.newsfeed.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String team;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.team = user.getTeam();
    }
}
