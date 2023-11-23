package com.sparta.newsfeed.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 필드를 세팅하기위해 주입
public class UserRequestDto { // signup, 회원가입시 2개의 정보를 받을건데 여기에 입력제한을 걸기위해서 @Pattern 사용
    @Pattern(regexp = "^[a-z0-9]{4,10}")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}")
    private String password;

    public String team;
}

