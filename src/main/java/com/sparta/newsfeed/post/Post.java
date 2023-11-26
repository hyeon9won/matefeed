package com.sparta.newsfeed.post;

import com.sparta.newsfeed.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Post extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String team;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Post(PostRequestDto requestDto) {
        this.team = requestDto.getTeam();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.createdAt =LocalDateTime.now();
        this.isCompleted = false;
    }

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 서비스 메서드

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
