package com.carepay.assignment.service;

import com.carepay.assignment.domain.CreatePostRequest;
import com.carepay.assignment.domain.Post;
import com.carepay.assignment.domain.PostDetails;
import com.carepay.assignment.domain.PostInfo;
import com.carepay.assignment.exception.NotFoundException;
import com.carepay.assignment.exception.ServiceValidationException;
import com.carepay.assignment.repository.PostRepository;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final Validator validator;

    public PostServiceImpl(PostRepository postRepository ){
        this.postRepository = postRepository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);

        if (!violations.isEmpty()) {
            throw new ServiceValidationException(violations.stream().findFirst().get().getMessage());
        }

        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .build();

        Post saved = postRepository.save(post);
        return mapToDetailDTO(saved);
    }

    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {
        Page<Post> entities = this.postRepository.findAll(pageable);

        List<Post> listOfPosts = entities.getContent();
        List<PostInfo> content= listOfPosts.stream().map(this::mapToDTO).collect(Collectors.toList());

        return new PageImpl<>(content);
    }

    @Override
    public PostDetails getPostDetails(Long id) {
        Optional<Post> entity = postRepository.findById(id);

        if ( !entity.isPresent() ) {
            throw new NotFoundException("Post not registered with Id " + id);
        }

        return mapToDetailDTO(entity.get());
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)){
            throw new NotFoundException("Post not registered with Id " + id);
        }
        postRepository.deleteById(id);
    }

    private PostInfo mapToDTO(Post post) {
        return new PostInfo(post.getId(), post.getTitle());
    }

    private PostDetails mapToDetailDTO(Post post) {
        return PostDetails.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
