package com.sparta.newsfeed.post;

import com.sparta.newsfeed.responseDto.CommonResponseDto;
import com.sparta.newsfeed.user.UserDTO;
import com.sparta.newsfeed.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> getPost(
            @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> getPost(@PathVariable Long postId) {
        try {
            PostResponseDto responseDto = postService.getPost(postId);
            return ResponseEntity.ok().body(responseDto);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 목록 조회
    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> getPostList() {
        List<PostListResponseDto> response = new ArrayList<>();

        Map<UserDTO, List<PostResponseDto>> responseDtoMap = postService.getUserPostMap();

        responseDtoMap.forEach((key, value) -> response.add(new PostListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> putPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.updatePost(postId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("게시물이 수정되었습니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deletePost(postId, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("게시물이 삭제되었습니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
