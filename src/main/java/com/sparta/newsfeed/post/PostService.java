package com.sparta.newsfeed.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto addPost(PostAddRequestDto requestDto) {
        // Dto -> Entity
        PostEntity postEntity = new PostEntity(requestDto);
        PostEntity savePost = postRepository.save(postEntity);
        return new PostResponseDto(savePost);
    }

    public PostResponseDto getPost(Long postId) {
        PostEntity postEntity = getPostEntity(postId);
        return new PostResponseDto(postEntity);
    }


    public List<PostResponseDto> getPost() {
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        List<PostEntity> postList = postRepository.findAllByOrderByCreatedAtDesc();
        for (PostEntity postEntity : postList) {
            PostResponseDto responseDto = new PostResponseDto(postEntity);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto requestDto) {
        PostEntity postEntity = getPostEntity(postId);

        if (!postEntity.getPassword().equals(requestDto.getPassword())) {
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");
        }
        postEntity.update(requestDto);
        postRepository.save(postEntity);

        return new PostResponseDto(postEntity);

    }

    public void deletePost(Long postId, String password) {
        PostEntity postEntity = getPostEntity(postId);

        if (!postEntity.passwordMatches(password)) {
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");
        }
        postRepository.delete(postEntity);
    }

    private PostEntity getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("해당 게시글을 찾을 수 없습니다."));
    }
}