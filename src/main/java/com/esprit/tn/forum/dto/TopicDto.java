package com.esprit.tn.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream
import java.time.Instant;

=======
>>>>>>> Stashed changes
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
