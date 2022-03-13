package com.szymek.socializr.mapper;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.model.PostLabel;
import com.szymek.socializr.repository.PostLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostLabelMapperImpl implements PostLabelMapper {

    private final PostLabelRepository postLabelRepository;

    @Override
    public Collection<PostLabel> map(List<String> postLabels, Post post) {
        return postLabels
                .stream()
                .map(label -> postLabelRepository.save(
                        PostLabel
                                .builder()
                                .name(label)
                                .post(post)
                                .build()
                )
                ).collect(Collectors.toList());
    }

    @Override
    public List<String> map(Collection<PostLabel> postLabels) {
        return postLabels
                .stream()
                .map(
                        PostLabel::getName
                )
                .collect(Collectors.toList());
    }
}
