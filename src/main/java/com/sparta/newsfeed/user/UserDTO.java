package com.sparta.newsfeed.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String team;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.team = user.getTeam();
    }
}
