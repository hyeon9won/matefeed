package com.sparta.newsfeed.jwt;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, java.io.IOException {
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        if (token != null && jwtUtil.validateToken(token)) {
            String key = "JWT_TOKEN:" + jwtUtil.getUserInformToken(token);
            String storedToken = redisTemplate.opsForValue().get(key);

            //**로그인 여부 체크**
            if(redisTemplate.hasKey(key) && storedToken != null) {
                Authentication authentication = (Authentication) jwtUtil.getUserInformToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}