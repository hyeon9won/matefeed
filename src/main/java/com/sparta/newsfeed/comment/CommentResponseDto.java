package com.sparta.newsfeed.comment;

import com.sparta.newsfeed.responseDto.CommonResponseDto;
import com.sparta.newsfeed.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponseDto extends CommonResponseDto {
    private Long id;
    private String text;
    private UserDTO user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDTO(comment.getUser());
        this.createDate = comment.getCreateDate();
    }
}
