package com.example.codeNinjas.service;

import com.example.codeNinjas.dto.TopicDto;
import com.example.codeNinjas.exceptions.ForumException;
import com.example.codeNinjas.mapper.TopicMapper;
import com.example.codeNinjas.model.Topic;
import com.example.codeNinjas.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Transactional
    public TopicDto save(TopicDto topicDto) {
        Topic save = topicRepository.save(topicMapper.mapDtoToTopic(topicDto));
        topicDto.setId(save.getId());
        return topicDto;
    }

    @Transactional(readOnly = true)
    public List<TopicDto> getAll() {
        return topicRepository.findAll()
                .stream()
                .map(topicMapper::mapTopicToDto)
                .collect(toList());
    }

    public TopicDto getSubreddit(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ForumException("No subreddit found with ID - " + id));
        return topicMapper.mapTopicToDto(topic);
    }
}
