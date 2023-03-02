package com.esprit.tn.forum.service;

import com.esprit.tn.forum.dto.PostRequest;
import com.esprit.tn.forum.mapper.CommentMapper;
import com.esprit.tn.forum.dto.CommentsDto;
import com.esprit.tn.forum.exceptions.PostNotFoundException;
import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.CommentRepository;
import com.esprit.tn.forum.repository.PostRepository;
import com.esprit.tn.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
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
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final NotificationService notificationService;

    @Transactional
    public void save(CommentsDto commentsDto) {
        User user = authService.getCurrentUser();
        filterCommentWithBadWords(commentsDto);
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, user);

        awardCommentBadges(user);

        commentRepository.save(comment);


//
//        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
//        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }


    @Transactional
    public void filterCommentWithBadWords(CommentsDto commentsDto) {
        // List of bad words to filter
        List<String> badWords = Arrays.asList("aaa", "badword2", "badword3");

        // Check if the post contains any bad words
        for (String word : badWords) {
            if (commentsDto.getText().toLowerCase().contains(word)) {
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
                String content = commentsDto.getText().toLowerCase().replace(word, "***");
                commentsDto.setText(content);
            }
        }
    }

    public void awardCommentBadges(User user) {
        int commentCount = commentRepository.countByUser(user);
        if (commentCount> 10) {
            user.setCommentBadge(BadgeType.GOLD);
        } else if (commentCount > 5) {
            user.setCommentBadge(BadgeType.SILVER);
        } else if (commentCount > 0) {
            user.setCommentBadge(BadgeType.BRONZE);
        }

        userRepository.save(user);
    }
}
