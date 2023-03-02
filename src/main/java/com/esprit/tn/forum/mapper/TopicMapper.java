package com.esprit.tn.forum.mapper;

<<<<<<< Updated upstream
import com.esprit.tn.forum.model.Post;
import com.esprit.tn.forum.model.Topic;
import com.esprit.tn.forum.dto.TopicDto;
=======
import com.esprit.tn.forum.dto.TopicDto;
import com.esprit.tn.forum.model.Post;
import com.esprit.tn.forum.model.Topic;
>>>>>>> Stashed changes
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(topic.getPosts()))")
    TopicDto mapTopicToDto(Topic topic);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Topic mapDtoToTopic(TopicDto topicDto);
}
