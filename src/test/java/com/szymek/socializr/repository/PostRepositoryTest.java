package com.szymek.socializr.repository;

import com.szymek.socializr.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    public static final String TEXT = "Test text";

    @Autowired
    PostRepository postRepository;

    @Test
    public void shouldSavePost(){
        Post expectedPostObject = Post.builder().text(TEXT).build();

        Post actualPostObject = postRepository.save(expectedPostObject);

        assertThat(actualPostObject).usingRecursiveComparison()
                .isEqualTo(expectedPostObject);
    }

}