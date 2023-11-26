package com.sparta.newsfeed.post;

import com.sparta.newsfeed.user.User;
import com.sparta.newsfeed.user.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post    (requestDto);
        post.setUser(user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    public PostResponseDto getPostDto(Long postId) {
        Post post = getPost(postId);
        return new PostResponseDto(post);
    }


    public Map<UserDTO, List<PostResponseDto>> getUserPostMap() {
        Map<UserDTO, List<PostResponseDto>> userPostMap = new HashMap<>();

        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        postList.forEach(post -> {
            var userDto = new UserDTO(post.getUser());
            var postDto = new PostResponseDto(post);
            if (userPostMap.containsKey(userDto)) {
                userPostMap.get(userDto).add(postDto);
            } else {
                userPostMap.put(userDto, new ArrayList<>(List.of(postDto)));
            }
        });
        return userPostMap;
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = getUserPost(postId, user);

        post.setTeam(requestDto.getTeam());
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

 /*   @Transactional
    public PostResponseDto completePost(Long postId, User user) {
        Post post = getUserPost(postId, user);

        post.complete();
        return  new PostResponseDto(post);
    } */


    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

    }

    public Post getUserPost(Long postId, User user) {
        Post post = getPost(postId);
        if(!user.getId().equals(post.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return post;
    }


    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        if (!post.getUser().equals(user)) {
            throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

}