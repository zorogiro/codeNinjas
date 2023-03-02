package com.esprit.tn.forum;

import com.esprit.tn.forum.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
<<<<<<< Updated upstream
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
=======

@SpringBootApplication
@EnableAsync
>>>>>>> Stashed changes
@Import(SwaggerConfiguration.class)
public class ForumApplication {

    public static void main(String[] args) {
<<<<<<< Updated upstream
        SpringApplication.run(ForumApplication.class, args);
=======
        SpringApplication.run(com.esprit.tn.forum.ForumApplication.class, args);
>>>>>>> Stashed changes
    }

}
