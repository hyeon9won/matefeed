package com.sparta.newsfeed.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 15)
    private String author;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 500)
    private String contents;

    public PostEntity(PostAddRequestDto requestDto) {
        this.id = requestDto.getId();
        this.category = requestDto.getCategory();
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword(); // 암호화 생략
        this.contents = requestDto.getContent();
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.contents = requestDto.getContent();
    }

    public boolean passwordMatches(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
