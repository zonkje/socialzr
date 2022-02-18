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
/*

        User u1 = User.builder().firstName("Szymek").lastName("Ptyskowski").contactInformation(null).posts(null).socialGroups(null).build();
        Address a1 = Address.builder().address("Espl. des Particules 1, 1211").city("Geneva").state("Meyrin").country("Switzerland").build();
        ContactInformation ci1 = ContactInformation.builder().email("szymek@gmail.com").phoneNumber("797 124 801").address(a1).build();
        Post p1 = Post.builder().text("Very first post in this page").author(u1).comments(null).build();
        Comment c1 = Comment.builder().text("first comment").author(u1).post(p1).build();
        SocialGroup sg1 = SocialGroup.builder().name("Pioneers").description("First group ever created for this service").creator(u1).members(null)
                .accessLevel(AccessLevel.PUBLIC).build();

        u1.setPosts(new HashSet<>());
        u1.getPosts().add(p1);
        u1.setContactInformation(ci1);
        p1.setComments(new HashSet<>());
        p1.getComments().add(c1);
        u1.setSocialGroups(new HashSet<>());
        u1.getSocialGroups().add(sg1);
        sg1.setMembers(new HashSet<>());
        sg1.getMembers().add(u1);

        userRepository.save(u1);
        contactInformationRepository.save(ci1);
        postRepository.save(p1);
        commentRepository.save(c1);
        socialGroupRepository.save(sg1);
*/

    }
}
