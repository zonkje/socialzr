package com.szymek.socializr.bootstrap;

import com.szymek.socializr.model.*;
import com.szymek.socializr.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final PostThumbUpRepository postThumbUpRepository;
    private final CommentThumbUpRepository commentThumbUpRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = User.builder().firstName("Szymek").lastName("Ptyskowski").contactInformation(null).posts(null).socialGroups(null).build();
        Address a1 = Address.builder().address("Espl. des Particules 1, 1211").city("Geneva").state("Meyrin").country("Switzerland").build();
        ContactInformation ci1 = ContactInformation.builder().email("szymek@gmail.com").phoneNumber("797124801").address(a1).build();
        PostLabel pl1 = PostLabel.builder().name("SELL").build();
        PostLabel pl2 = PostLabel.builder().name("LOCAL").build();
        Post p1 = Post.builder().text("Very first post in this page").author(u1).comments(null).postLabels(null).postThumbUps(null).build();
        Comment c1 = Comment.builder().text("first comment").author(u1).post(p1).commentThumbUps(null).build();
        SocialGroup sg1 = SocialGroup.builder().name("Pioneers").description("First group ever created for this service").creator(u1).members(null)
                .accessLevel(AccessLevel.PUBLIC).build();
        PostThumbUp ptu1 = PostThumbUp.builder().post(p1).author(u1).build();
        CommentThumbUp ctu1 = CommentThumbUp.builder().comment(c1).author(u1).build();

        p1.setPostThumbUps(new ArrayList<>());
        c1.setCommentThumbUps(new ArrayList<>());
        u1.setPosts(new ArrayList<>());
        u1.getPosts().add(p1);
        u1.setContactInformation(ci1);
        p1.setComments(new ArrayList<>());
        p1.setPostLabels(new ArrayList<>());
        p1.getComments().add(c1);
        p1.getPostLabels().add(pl1);
        p1.getPostLabels().add(pl2);
        pl1.setPost(p1);
        pl2.setPost(p1);
        p1.getPostThumbUps().add(ptu1);
        c1.getCommentThumbUps().add(ctu1);
        u1.setSocialGroups(new ArrayList<>());
        u1.getSocialGroups().add(sg1);
        sg1.setMembers(new ArrayList<>());
        sg1.getMembers().add(u1);

        contactInformationRepository.save(ci1);
        userRepository.save(u1);
        postRepository.save(p1);
        commentRepository.save(c1);
        socialGroupRepository.save(sg1);
        postThumbUpRepository.save(ptu1);
        commentThumbUpRepository.save(ctu1);
    }
}
