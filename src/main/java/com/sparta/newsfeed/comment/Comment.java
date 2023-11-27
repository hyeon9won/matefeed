package com.sparta.newsfeed.comment;

import com.sparta.newsfeed.post.Post;
import com.sparta.newsfeed.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment { //post와 연관관계를 맺어야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column
    private LocalDateTime createDate;

    public Comment(CommentRequestDto dto) {
        this.text = dto.getText();
        this.createDate = LocalDateTime.now();
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    // 서비스 메서드
    public void setText(String text) {
        this.text = text;
    }
}

