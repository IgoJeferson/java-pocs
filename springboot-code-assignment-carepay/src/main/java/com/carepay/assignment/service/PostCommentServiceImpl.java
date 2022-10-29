package com.carepay.assignment.service;

import com.carepay.assignment.domain.CreatePostCommentRequest;
import com.carepay.assignment.domain.PostComment;
import com.carepay.assignment.domain.PostCommentDetails;
import com.carepay.assignment.exception.NotFoundException;
import com.carepay.assignment.exception.ServiceValidationException;
import com.carepay.assignment.repository.PostCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final Validator validator;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository ){
        this.postCommentRepository = postCommentRepository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public PostCommentDetails createPostComment(@Valid CreatePostCommentRequest request) {
        Set<ConstraintViolation<CreatePostCommentRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ServiceValidationException(violations.stream().findFirst().get().getMessage());
        }

        PostComment postComment = PostComment.builder()
                .postId(request.getPostId())
                .comment(request.getComment())
                .build();

        PostComment saved = postCommentRepository.save(postComment);
        return mapToDTO(saved);
    }

    @Override
    public Page<PostCommentDetails> getPostComments(Long postId, Pageable pageable) {
        Page<PostComment> entities = postCommentRepository.findByPostId(postId, pageable);

        List<PostComment> listOfComments = entities.getContent();
        List<PostCommentDetails> content= listOfComments.stream().map(this::mapToDTO).collect(Collectors.toList());

        return new PageImpl<>(content);
    }

    @Override
    public PostCommentDetails getPostCommentDetails(Long postId, Long id) {
        Optional<PostComment> entity = postCommentRepository.findByPostIdAndId(postId, id);

        if ( !entity.isPresent() ) {
            throw new NotFoundException("Comment not registered with Id " + id);
        }

        return mapToDTO(entity.get());
    }

    @Override
    public void deletePostComment(Long postId, Long id) {
        if (!postCommentRepository.existsByPostIdAndId(postId, id)){
            throw new NotFoundException("Comment not registered with Id " + id);
        }
        postCommentRepository.deleteById(id);
    }

    private PostCommentDetails mapToDTO(PostComment postComment) {
        return PostCommentDetails.builder()
                .id(postComment.getId())
                .postId(postComment.getPostId())
                .comment(postComment.getComment())
                .build();
    }
}
