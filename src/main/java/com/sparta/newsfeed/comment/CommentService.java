package com.sparta.newsfeed.comment;

import com.sparta.newsfeed.post.Post;
import com.sparta.newsfeed.post.PostResponseDto;
import com.sparta.newsfeed.post.PostService;
import com.sparta.newsfeed.responseDto.ResponseDto;
import com.sparta.newsfeed.user.User;
import com.sparta.newsfeed.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentResponseDto createComment(CommentRequestDto dto, User user) {
        Post post = postService.getPost(dto.getPostId());

        Comment comment = new Comment(dto);
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDTO, User user) {
        Comment comment = getUserComment(commentId, user);

        comment.setText(commentRequestDTO.getText());

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = getUserComment(commentId, user);

        commentRepository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

        if(!user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }

    public Map<UserDTO, List<CommentResponseDto>> getUserCommentMap() {
        Map<UserDTO, List<CommentResponseDto>> userCommentMap = new HashMap<>();

        List<Comment> commentList = commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        commentList.forEach(comment -> {
            var userDto = new UserDTO(comment.getUser());
            var commentDto = new CommentResponseDto(comment);
            if (userCommentMap.containsKey(userDto)) {
                userCommentMap.get(userDto).add(commentDto);
            } else {
                userCommentMap.put(userDto, new ArrayList<>(List.of(commentDto)));
            }
        });
        return userCommentMap;
    }
}
