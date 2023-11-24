package com.sparta.newsfeed.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.CommonResponseDto;
import com.sparta.newsfeed.user.UserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);// 토큰을 가져오기위해서 JwtUtil 필요하며 bearer글자가 잘린 정보를 받아옴
        System.out.println(token);
        if(Objects.nonNull(token)) { // 토큰이 null여부 확인
            if(jwtUtil.validateToken(token)) { // 토큰을 실제 검증을 할 메소드 validateToken(token) 주입, 토큰이 들어왔을때 Exception 상황이 일어나지 않으면 정상적으로 작동
                Claims info = jwtUtil.getUserInformToken(token); // 토큰이 정상일 경우 유저정보를 가져와야하는데 Claims 정보 = 유저정보가 되게끔하여 메소드 getUserInformToken 를 만들어서 token에서 가져오도록 한다.

                // 인증정보에 유저정보(username) 넣기
                // username -> user 조회   (UsernamePasswordAuthenticationToken)
                String username = info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                // -> userDetails 에 담고
                UserDetails userDetails = userDetailsService.getUserDetails(username);
                // -> authentication의 principal에 담고
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // -> securityContext 에 담고
                context.setAuthentication(authentication);
                // -> SecurityContextHolder 에 담고
                SecurityContextHolder.setContext(context);
                // -> 이제 @AuthenticationPrincipal 로 조회 할 수 있음

            } else {
                // 인증정보가 존재하지 않을때 response
                CommonResponseDto commonResponseDto = new CommonResponseDto("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 응답값에 Status를 세팅하기위해 필요
                response.setContentType("application/json; charset=UTF-8");// Body 부분이 깨지지 않게 하기위해서
                response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto)); //
                return;
            }
        }

        filterChain.doFilter(request, response); // 로그인처리 완료시 filterChanim에서 doFilter로 넘어갈수있도록 처리


    }
}
