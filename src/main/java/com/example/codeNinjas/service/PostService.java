package com.example.codeNinjas.service;

import com.example.codeNinjas.dto.PostRequest;
import com.example.codeNinjas.dto.PostResponse;
import com.example.codeNinjas.exceptions.ForumException;
import com.example.codeNinjas.exceptions.PostNotFoundException;
import com.example.codeNinjas.exceptions.TopicNotFoundException;
import com.example.codeNinjas.mapper.PostMapper;
import com.example.codeNinjas.model.Post;
import com.example.codeNinjas.model.Topic;
import com.example.codeNinjas.model.User;
import com.example.codeNinjas.repository.PostRepository;
import com.example.codeNinjas.repository.TopicRepository;
import com.example.codeNinjas.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Topic subreddit = topicRepository.findByName(postRequest.getTopicName())
                .orElseThrow(() -> new ForumException(postRequest.getTopicName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Topic subreddit = topicRepository.findById(subredditId)
                .orElseThrow(() -> new TopicNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
