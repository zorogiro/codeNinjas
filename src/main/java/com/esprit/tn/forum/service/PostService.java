package com.esprit.tn.forum.service;

import com.esprit.tn.forum.dto.PostRequest;
import com.esprit.tn.forum.dto.PostResponse;
import com.esprit.tn.forum.exceptions.PostNotFoundException;
import com.esprit.tn.forum.exceptions.TopicNotFoundException;
import com.esprit.tn.forum.mapper.PostMapper;
import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.ArchiveRepository;
import com.esprit.tn.forum.repository.PostRepository;
import com.esprit.tn.forum.repository.TopicRepository;
import com.esprit.tn.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
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
    private final MailService mailService;

    private final ArchiveRepository archiveRepository;

    public void save(PostRequest postRequest) {
        User user = authService.getCurrentUser();
        Topic topic = topicRepository.findByName(postRequest.getTopicName())
                .orElseThrow(() -> new TopicNotFoundException(postRequest.getTopicName()));
        filterPostWithBadWords(postRequest);
        awardPostBadges(user);
        postRepository.save(postMapper.map(postRequest, topic, user));
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

    public void filterPostWithBadWords(PostRequest postRequest) {
        // List of bad words to filter
        List<String> badWords = Arrays.asList("aaa", "badword2", "badword3");

        // Check if the post contains any bad words
        for (String word : badWords) {
            if (postRequest.getDescription().toLowerCase().contains(word)) {
                // Increment the alert count for the user who made the post
                User user = authService.getCurrentUser();
                int alertCount = user.getAlertCount();
                user.setAlertCount(alertCount + 1);
                //notificationService.sendNotification(user, "your alert count is now " + alertCount + "remember that you will be ban ished after the 3rd bad word ");

                // If the user has been alerted three times, ban them for three days
                if (alertCount >= 3) {
                    user.setBannedUntil(Instant.now().plus(Duration.ofDays(3)));
                    mailService.sendMail(new NotificationEmail("your account is now blocked for 3 days ",
                            user.getEmail(), "Thank you for uderstanding, " +
                            "please click on the below url to send an e3tiradh  : " +
                            "http://localhost:8081/api/auth/e3tiradh/"));
                }

                // Remove the bad word from the post
                String content = postRequest.getDescription().toLowerCase().replace(word, "***");
                postRequest.setDescription(content);
            }
        }
    }
    public void archivePostAfter3Days(Post post) {
        long currentTimeMillis = System.currentTimeMillis();
        long deleteTimeMillis = post.getDeletedTime().getTime();
        long threeDaysInMillis = 3 * 24 * 60 * 60 * 1000;
        if (post.isDeleted() && (currentTimeMillis - deleteTimeMillis) > threeDaysInMillis) {
            archivePost(post.getPostId());
        }
    }

    public void deleteOldDeletedPosts() {
        List<Post> deletedPosts = postRepository.findAllByDeletedTrue();
        for (Post post : deletedPosts) {
            archivePostAfter3Days(post);
        }
    }
    @Scheduled(cron = "0 0 0 * * ?") // every day at 00:00
    public void deleteOldDeletedPostsDaily() {
        deleteOldDeletedPosts();
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
            archivePost(postRepository.findById(postId).get().getPostId());
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

    public void archivePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id - " + postId));

        if (!post.getUser().getUserId().equals(authService.getCurrentUser().getUserId()) && !post.getUser().isAdmin(authService.getCurrentUser()) ) {
            throw new AccessDeniedException("You can only archive your own posts.");
        }

        Archive postArchive = new Archive();
        postArchive.setPostId(post.getPostId());
        postArchive.setTitle(post.getPostName());
        postArchive.setContent(post.getDescription());
        postArchive.setUser(post.getUser());
        postArchive.setCreatedDate(post.getCreatedDate());
        postArchive.setArchivedDate(Instant.now());

        archiveRepository.save(postArchive);
        postRepository.delete(post);
    }

}

