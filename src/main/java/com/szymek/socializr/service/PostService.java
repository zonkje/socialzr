package com.szymek.socializr.service;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.model.Post;

import java.util.Collection;

public interface PostService extends CrudService<PostDTO, Long>{

    Collection<Post> findAllByAuthor();

}
