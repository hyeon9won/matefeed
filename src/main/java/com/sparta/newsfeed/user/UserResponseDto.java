package com.sparta.newsfeed.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String username;
// private String password;
    private String team;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
// this.password = user.getPassword();
        this.team = user.getTeam();
    }
}
