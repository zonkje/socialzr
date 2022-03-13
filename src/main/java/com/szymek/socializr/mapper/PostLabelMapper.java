package com.szymek.socializr.mapper;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.model.PostLabel;

import java.util.Collection;
import java.util.List;

public interface PostLabelMapper {

    Collection<PostLabel> map(List<String> postLabels, Post post);
    List<String> map(Collection<PostLabel> postLabels);

}
