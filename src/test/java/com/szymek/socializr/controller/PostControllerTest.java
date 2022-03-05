package com.szymek.socializr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(PostController.class)
@ComponentScan(basePackages = "com/szymek/socializr/mapper")
class PostControllerTest {

    public static final String TEXT = "Test text";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

/*    @Mock
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    PostController postController;

    MockMvc mockMvc;*/

    PostDTO postDTO;

    @BeforeEach
    void setUp() {
        postDTO = new PostDTO();
        postDTO.setId(1L);
        postDTO.setText(TEXT);
        postDTO.setAuthorId(1L);
    }

    @Test
    void getPost() throws Exception {

        given(postService.findById(any())).willReturn(postDTO);

        mockMvc.perform(get("/post/{postId}", postDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(postDTO.getId().intValue())))
                .andExpect(jsonPath("$.text", is(postDTO.getText())))
                .andDo(document("/post",
                        pathParameters(
                                parameterWithName("postId")
                                        .description("ID of the desired post to get.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("ID of the post"),
                                fieldWithPath("text").description("Text of the post"),
                                fieldWithPath("authorId").description("Post author ID")
                        )));
    }

    @Test
    @DisplayName("Should list all posts when making GET request to endpoint /post")
    void getAllPosts() throws Exception {
        PostDTO postDTO1 = new PostDTO();
        PostDTO postDTO2 = new PostDTO();
        postDTO1.setId(1L);
        postDTO2.setId(2L);
        postDTO1.setText(TEXT);
        postDTO2.setText(TEXT + "2");

        Mockito.when(postService.findAll()).thenReturn(asList(postDTO1, postDTO2));

        mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[0].text", Matchers.is(TEXT)))
                .andExpect(jsonPath("$[1].text", Matchers.is(TEXT + "2")));
    }

    @Test
    void createPost() throws Exception {

        String postDTOJson = objectMapper.writeValueAsString(postDTO);

        ConstrainedFields fields = new ConstrainedFields(PostDTO.class);

        //TODO -fix constraints
        //TODO -add attributes [https://thoughts.tostring.blog/custom-attributes-in-spring-restdocs/]
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postDTOJson))
                .andExpect(status().isCreated())
                .andDo(document("/post/",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("text").description("Main content of the post"),
                                fields.withPath("authorId").ignored()
                        )));
    }

    private static class ConstrainedFields {

                private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
        /*private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {

            ResourceBundleConstraintDescriptionResolver fallback = new ResourceBundleConstraintDescriptionResolver();
            this.constraintDescriptions = new ConstraintDescriptions(input, (constraint) -> {
                String message = (String) constraint.getConfiguration().get("message");
                if (message != null && !Pattern.compile("\\{(.*?)\\}").matcher(message).matches()) {
                    return message;
                }
                return fallback.resolveDescription(constraint);
            });
        }

        public FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }*/
    }

}