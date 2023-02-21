package com.esprit.tn.forum.service;

import com.esprit.tn.forum.dto.PostRequest;
import com.esprit.tn.forum.dto.PostResponse;
import com.esprit.tn.forum.exceptions.PostNotFoundException;
import com.esprit.tn.forum.exceptions.TopicNotFoundException;
import com.esprit.tn.forum.mapper.PostMapper;
import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.PostRepository;
import com.esprit.tn.forum.repository.TopicRepository;
import com.esprit.tn.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private final MailService mailService;
    private final NotificationService notificationService;
    private final PostMapper postMapper;

    @Scheduled(cron = "0 0 0 * * ?") // every day at 00:00
    public void deleteOldDeletedPostsDaily() {
        deleteOldDeletedPosts();
    }

    public void save(PostRequest postRequest) {
        // Map the PostRequest to a Post object
        Topic topic = topicRepository.findByName(postRequest.getTopicName())
                .orElseThrow(() -> new TopicNotFoundException(postRequest.getTopicName()));
        User user = authService.getCurrentUser();
        Post post = postMapper.map(postRequest, topic, user);

        // Check if the post contains any bad words
        filterPostWithBadWords(post);

        // Check if the user claimed a new PostBadge
        awardPostBadges(user);

        // Save the post to the database
        postRepository.save(post);
    }


    public void filterPostWithBadWords(Post post) {
        // List of bad words to filter
        List<String> badWords = Arrays.asList("aaa", "badword2", "badword3");

        // Check if the comment contains any bad words
        for (String word : badWords) {
            if (post.getDescription().toLowerCase().contains(word)) {
                // Increment the alert count for the user who made the comment
                User user = post.getUser();
                int alertCount = user.getAlertCount();
                user.setAlertCount(alertCount + 1);
                notificationService.sendNotification(user,"your alert count is now " + alertCount + "remember that you will be ban ished after the 3rd bad word ");


                // If the user has been alerted three times, ban them for three days
                if (alertCount >= 3) {
                    user.setBannedUntil(LocalDateTime.now().plusDays(3));
                    mailService.sendMail(new NotificationEmail("your account is now blocked for 3 days ",
                            user.getEmail(), "Thank you for uderstanding, " +
                            "please click on the below url to send an e3tiradh  : " +
                            "http://localhost:8081/api/auth/e3tiradh/"));


                }

                // Remove the bad word from the comment
                String content = post.getDescription().toLowerCase().replace(word, "***");
                post.setDescription(content);
            }
        }
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllVisiblePosts() {
        List<PostResponse> list = postRepository.findAllByDeleted(false)
                .stream()
                .map(postMapper::mapToDto)
                .sorted(Comparator.comparingInt(PostResponse::getVoteCount).reversed())
                .collect(Collectors.toList());
        return list;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllUnvisiblePosts() {
        return postRepository.findAllByDeleted(true)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());

    }

    public void deletePostAfter3Days(Post post) {
        long currentTimeMillis = System.currentTimeMillis();
        long deleteTimeMillis = post.getDeletedTime().getTime();
        long threeDaysInMillis = 3 * 24 * 60 * 60 * 1000;
        if (post.isDeleted() && (currentTimeMillis - deleteTimeMillis) > threeDaysInMillis) {
            postRepository.delete(post);
        }
    }

    public void deleteOldDeletedPosts() {
        List<Post> deletedPosts = postRepository.findAllByDeletedTrue();
        for (Post post : deletedPosts) {
            deletePostAfter3Days(post);
        }
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));
        List<Post> posts = postRepository.findAllByTopic(topic);
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

    @Transactional(readOnly = true)
    public boolean deletePost(Long postId) {
        // Get the current user from the authentication service
        User currentUser = authService.getCurrentUser();

        // Get the post to delete from the post repository
        Post postToDelete = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        // Check if the user is authorized to delete the post
        if (postToDelete.getUser().equals(currentUser)) {
            postToDelete.setDeleted(true);
            postRepository.save(postToDelete);
            return true;
        } else if (currentUser.isAdmin(currentUser)) {
            postRepository.delete(postRepository.findById(postId).get());
        }

        // User is not authorized to delete the post
        return false;
    }

    public void awardPostBadges(User user) {
            int postCount = postRepository.countByUser(user);
            if (postCount > 10) {
                user.setPostBadge(BadgeType.GOLD);
            } else if (postCount > 5) {
                user.setPostBadge(BadgeType.SILVER);
            } else if (postCount > 0) {
                user.setPostBadge(BadgeType.BRONZE);
            }
            userRepository.save(user);
        }
    }



