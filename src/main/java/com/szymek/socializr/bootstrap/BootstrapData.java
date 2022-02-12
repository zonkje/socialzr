package com.szymek.socializr.bootstrap;

import com.szymek.socializr.model.*;
import com.szymek.socializr.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final ContactInformationRepository contactInformationRepository;

    public BootstrapData(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, SocialGroupRepository socialGroupRepository, ContactInformationRepository contactInformationRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.socialGroupRepository = socialGroupRepository;
        this.contactInformationRepository = contactInformationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User("Szymek", "Ptyskowski", null, null, null);
        Address a1 = new Address("Espl. des Particules 1, 1211", "Meyrin", "Geneva", "Switzerland");
        ContactInformation ci1 = new ContactInformation("szymek@gmail.com", "797 124 801", a1);
        Post p1 = new Post("Very first post in this page", u1, null);
        Comment c1 = new Comment("first comment", u1, p1);
        SocialGroup sg1 = new SocialGroup("Pioneers", "First group ever created for this service", u1, null);
        u1.setPosts(new HashSet<>());
        u1.getPosts().add(p1);
        u1.setContactInformation(ci1);
        p1.setComments(new HashSet<>());
        p1.getComments().add(c1);
        u1.setSocialGroups(new HashSet<>());
        u1.getSocialGroups().add(sg1);
        sg1.setMembers(new HashSet<>());
        sg1.getMembers().add(u1);

        contactInformationRepository.save(ci1);
        userRepository.save(u1);
        postRepository.save(p1);
        commentRepository.save(c1);
        socialGroupRepository.save(sg1);

    }
}
