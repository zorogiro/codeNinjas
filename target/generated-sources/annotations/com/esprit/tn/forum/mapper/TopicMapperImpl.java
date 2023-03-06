package com.esprit.tn.forum.mapper;

import com.esprit.tn.forum.dto.TopicDto;
import com.esprit.tn.forum.dto.TopicDto.TopicDtoBuilder;
import com.esprit.tn.forum.model.Topic;
import com.esprit.tn.forum.model.Topic.TopicBuilder;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-06T22:30:39+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class TopicMapperImpl implements TopicMapper {

    @Override
    public TopicDto mapTopicToDto(Topic topic) {
        if ( topic == null ) {
            return null;
        }

        TopicDtoBuilder topicDto = TopicDto.builder();

        topicDto.id( topic.getId() );
        topicDto.name( topic.getName() );
        topicDto.description( topic.getDescription() );

        topicDto.numberOfPosts( mapPosts(topic.getPosts()) );

        return topicDto.build();
    }

    @Override
    public Topic mapDtoToTopic(TopicDto topicDto) {
        if ( topicDto == null ) {
            return null;
        }

        TopicBuilder topic = Topic.builder();

        topic.id( topicDto.getId() );
        topic.name( topicDto.getName() );
        topic.description( topicDto.getDescription() );

        return topic.build();
    }
}
