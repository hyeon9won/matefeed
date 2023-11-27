package com.sparta.newsfeed.comment;

import com.sparta.newsfeed.post.PostListResponseDto;
import com.sparta.newsfeed.post.PostResponseDto;
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
import java.util.concurrent.RejectedExecutionException;

@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> postComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<CommentListResponseDto>> getCommentList() {
        List<CommentListResponseDto> response = new ArrayList<>();

        Map<UserDTO, List<CommentResponseDto>> responseDtoMap = commentService.getUserCommentMap();

        responseDtoMap.forEach((key, value) -> response.add(new CommentListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto responseDto = commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("삭제가 완료되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}