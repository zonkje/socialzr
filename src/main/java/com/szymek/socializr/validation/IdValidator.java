package com.szymek.socializr.validation;

import com.szymek.socializr.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class IdValidator implements ConstraintValidator<ValidId, Object> {

    private String entityName;

    private final PostLabelRepository postLabelRepository;
    private final PostRepository postRepository;
    private final PostThumbUpRepository postThumbUpRepository;
    private final CommentRepository commentRepository;
    private final CommentThumbUpRepository commentThumbUpRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final UserRepository userRepository;
    private final SocialGroupPostRepository socialGroupPostRepository;

    @Override
    public void initialize(ValidId constraintAnnotation) {
        entityName = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object pathVariable, ConstraintValidatorContext context) {
        boolean valid;

        /*
        TODO:
         -CHANGE THIS IMPLEMENTATION TO SOMETHING MORE 'CONVENIENT'.
            User generic or aspects, repos should be injected dynamically in runtime
         */

        if (!(pathVariable instanceof Long)) {
            String returnMessage = "ID must be a number";

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(returnMessage).addConstraintViolation();
            valid = false;
        } else {
            Long id = (Long) pathVariable;
            valid = switch (entityName) {
                case "PostLabel" -> postLabelRepository.existsById(id);
                case "Post" -> postRepository.existsById(id);
                case "PostThumbUp" -> postThumbUpRepository.existsById(id);
                case "Comment" -> commentRepository.existsById(id);
                case "CommentThumbUp" -> commentThumbUpRepository.existsById(id);
                case "ContactInformation" -> contactInformationRepository.existsById(id);
                case "SocialGroup" -> socialGroupRepository.existsById(id);
                case "User" -> userRepository.existsById(id);
                case "SocialGroupPost" -> socialGroupPostRepository.existsById(id);
                default -> false;
            };

            if (!valid) {

                String returnMessage = String.format("%s with ID: %s not found", entityName, id);

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(returnMessage).addConstraintViolation();
            }
        }

        return valid;
    }
}
