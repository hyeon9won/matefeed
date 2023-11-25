package com.sparta.newsfeed.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class PostController {

    private final PostService postService;

    private final PostRepository postRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto addPost(
            @RequestBody PostAddRequestDto requestDto
    ) {
        PostResponseDto responseDto = postService.addPost(requestDto);
        return responseDto;
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(
            @PathVariable Long postId
    ) {
        return postService.getPost(postId);
    }

    @GetMapping
    public List<PostResponseDto> getPosts() {
        return postService.getPost();

    }

    @GetMapping("/{team}")
    public List<PostEntity> getFeedByTeam(
            @PathVariable String team
    ) {
        return postRepository.findByTeam(team);
    }

    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto requestDto
    ) {
        return postService.updatePost(postId, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{postId}")
    public void deletePost(
            @PathVariable Long postId,
            @RequestHeader("password") String password
    ) {
        postService.deletePost(postId, password);
    }
}
