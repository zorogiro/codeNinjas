package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post_archive")
public class PostArchive {

    @Id
    private String id;

    private Long postId;

    private String title;

    private String content;

    private User user;

    private Instant createdDate;

    private Instant archivedDate;
}
