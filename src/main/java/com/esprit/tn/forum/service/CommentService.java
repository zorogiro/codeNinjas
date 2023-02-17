package com.esprit.tn.forum.service;

import com.esprit.tn.forum.mapper.CommentMapper;
import com.esprit.tn.forum.model.Post;
import com.esprit.tn.forum.dto.CommentsDto;
import com.esprit.tn.forum.exceptions.PostNotFoundException;
import com.esprit.tn.forum.model.Comment;
import com.esprit.tn.forum.model.NotificationEmail;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.CommentRepository;
import com.esprit.tn.forum.repository.PostRepository;
import com.esprit.tn.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

public void save(CommentsDto commentsDto) {
    // Check if the comment contains bad words
    filterCommentWithBadWords(commentsDto);

    Post post = postRepository.findById(commentsDto.getPostId())
            .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
    Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
    commentRepository.save(comment);

    String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
    sendCommentNotification(message, post.getUser());
}

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public void filterCommentWithBadWords(CommentsDto commentsDto) {
        // List of bad words to filter
        List<String> badWords = Arrays.asList("aaa", "badword2", "badword3");

        // Check if the comment contains any bad words
        for (String word : badWords) {
            if (commentsDto.getText().toLowerCase().contains(word)) {
                // Increment the alert count for the user who made the comment
                User user = authService.getCurrentUser();
                int alertCount = user.getAlertCount();
                user.setAlertCount(alertCount + 1);

                // If the user has been alerted three times, ban them for three days
                if (alertCount >= 3) {
                    user.setBannedUntil(LocalDateTime.now().plusDays(3));
                }

                // Remove the bad word from the comment
                String content = commentsDto.getText().toLowerCase().replace(word, "***");
                commentsDto.setText(content);
            }
        }
    }
}
