package com.sparta.newsfeed.comment;

import com.sparta.newsfeed.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentListResponseDto {
    private UserDTO user;
    private List<CommentResponseDto> commentList;

    public CommentListResponseDto(UserDTO user, List<CommentResponseDto> commentList) {
        this.user = user;
        this.commentList = commentList;
    }
}
