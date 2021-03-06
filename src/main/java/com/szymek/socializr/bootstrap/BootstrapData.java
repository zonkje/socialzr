package com.szymek.socializr.bootstrap;

import com.szymek.socializr.model.*;
import com.szymek.socializr.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.szymek.socializr.model.Role.USER;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SocialGroupPostRepository socialGroupPostRepository;
    private final CommentRepository commentRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final PostThumbUpRepository postThumbUpRepository;
    private final CommentThumbUpRepository commentThumbUpRepository;
    private final PostLabelRepository postLabelRepository;

    @Override
    public void run(String... args) throws Exception {

        String userAvatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Question_mark_%28bla" +
                "ck%29.svg/200px-Question_mark_%28black%29.svg.png";
        String groupAvatarUrl = "https://thumbs.dreamstime.com/b/question-mark-seamless-pattern-vector-marks-mo" +
                "nochrome-hipster-background-hand-drawn-random-black-punctuation-eps-197150276.jpg";

        User u1 = User.builder().firstName("Szymek").lastName("Ptyskowski").contactInformation(null)
                .posts(null).socialGroups(null).username("username").password("password123B!").role(USER)
                .avatarUrl(userAvatarUrl).build();
        Address a1 = Address.builder().address("Espl. des Particules 1").city("Geneva").state("Meyrin")
                .zipCode("1211").country("Switzerland").build();
        ContactInformation ci1 = ContactInformation.builder().email("szymek@gmail.com").phoneNumber("797124801")
                .address(a1).build();
        PostLabel pl1 = PostLabel.builder().name("TEST").build();
        PostLabel pl2 = PostLabel.builder().name("INFO").build();
        Post p1 = Post.builder().text("Very first post in this page").author(u1).comments(null).postLabels(null)
                .postThumbUps(null).build();
        Comment c1 = Comment.builder().text("first comment").author(u1).post(p1).commentThumbUps(null).build();
        SocialGroup sg1 = SocialGroup.builder().name("Pioneers")
                .description("First group ever created for this service").creator(u1).members(null)
                .accessLevel(AccessLevel.PUBLIC).avatarUrl(groupAvatarUrl).build();
        PostThumbUp ptu1 = PostThumbUp.builder().post(p1).author(u1).build();
        CommentThumbUp ctu1 = CommentThumbUp.builder().comment(c1).author(u1).build();
        SocialGroupPost sgp1 = SocialGroupPost.builder().text("Very first social group post in this page").author(u1)
                .comments(null).postLabels(null).postThumbUps(null).socialGroup(null).build();
        Comment c2 = Comment.builder().text("second comment").author(u1).post(sgp1).commentThumbUps(null).build();

        p1.setPostThumbUps(new ArrayList<>());
        p1.setPostLabels(new ArrayList<>());
        pl1.setPosts(new ArrayList<>());
        pl2.setPosts(new ArrayList<>());
        sgp1.setPostThumbUps(new ArrayList<>());
        c1.setCommentThumbUps(new ArrayList<>());
        c2.setCommentThumbUps(new ArrayList<>());
        u1.setPosts(new ArrayList<>());
        u1.getPosts().add(p1);
        u1.setContactInformation(ci1);
        p1.setComments(new ArrayList<>());
        sgp1.setComments(new ArrayList<>());
        p1.setPostLabels(new ArrayList<>());
        sgp1.setPostLabels(new ArrayList<>());
        p1.getComments().add(c1);
        sgp1.getComments().add(c2);
        p1.getPostLabels().add(pl1);
        p1.getPostLabels().add(pl2);
        pl1.getPosts().add(p1);
        pl2.getPosts().add(p1);
        p1.getPostThumbUps().add(ptu1);
        c1.getCommentThumbUps().add(ctu1);
        u1.setSocialGroups(new ArrayList<>());
        u1.getSocialGroups().add(sg1);
        sg1.setMembers(new ArrayList<>());
        sg1.getMembers().add(u1);
        sg1.setSocialGroupPosts(new ArrayList<>());
        sg1.getSocialGroupPosts().add(sgp1);
        sgp1.setSocialGroup(sg1);
        ci1.getWebsitesURLs().addAll(List.of("https://www.linkedin.com/company/docker/",
                "https://www.linkedin.com/company/github/"));

        contactInformationRepository.save(ci1);
        userRepository.save(u1);
        postRepository.save(p1);
        commentRepository.save(c1);
        socialGroupRepository.save(sg1);
        postThumbUpRepository.save(ptu1);
        commentThumbUpRepository.save(ctu1);
        postRepository.save(sgp1);
        socialGroupPostRepository.save(sgp1);
        postLabelRepository.save(pl1);
        postLabelRepository.save(pl2);
    }
}
