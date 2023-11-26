package com.sparta.newsfeed.post;

import com.sparta.newsfeed.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostListResponseDto {
    private UserDTO user;
    private List<PostResponseDto> postList;

    public PostListResponseDto(UserDTO user, List<PostResponseDto> postList) {
        this.user = user;
        this.postList = postList;
    }
}
