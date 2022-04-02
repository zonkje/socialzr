package com.szymek.socializr.mapper;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.model.PostLabel;
import com.szymek.socializr.repository.PostLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostLabelMapperImpl implements PostLabelMapper {

    private final PostLabelRepository postLabelRepository;

    @Override
    public Collection<PostLabel> map(List<String> postLabels, Post post) {
        if (postLabels == null) return Collections.emptyList();
        return postLabels
                .stream()
                .map(label -> {
                            Optional<PostLabel> optionalPostLabel = postLabelRepository.findPostLabelByName(label);
                            PostLabel postLabel;
                            if (optionalPostLabel.isEmpty()) {
                                postLabel = buildPostLabel(label, post);
                            } else {
                                postLabel = optionalPostLabel.get();
                                postLabel.getPosts().add(post);
                            }
                            return postLabelRepository.save(postLabel);
                        }
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

    private PostLabel buildPostLabel(String postLabelName, Post post) {

        PostLabel postLabel = PostLabel.builder().name(postLabelName).build();
        postLabel.getPosts().add(post);
        return postLabel;

    }
}
