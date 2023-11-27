package com.sparta.newsfeed.post;

import com.sparta.newsfeed.responseDto.CommonResponseDto;
import com.sparta.newsfeed.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto extends CommonResponseDto {

    private Long id;
    private String team;
    private String title;
    private String content;
    private Boolean isCompleted;
    private UserDTO user;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.team = post.getTeam();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.isCompleted = post.getIsCompleted();
        this.user    = new UserDTO(post.getUser());
        this.createdAt = post.getCreatedAt();
    }
}