package sylvestre01.vybediaryblog.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.BadRequestException;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.exception.UnauthorizedException;
import sylvestre01.vybediaryblog.model.Category;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.Tag;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.payload.PostPayload;
import sylvestre01.vybediaryblog.response.PostResponse;
import sylvestre01.vybediaryblog.repository.CategoryRepository;
import sylvestre01.vybediaryblog.repository.PostRepository;
import sylvestre01.vybediaryblog.repository.TagRepository;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.service.PostService;
import sylvestre01.vybediaryblog.utils.AppConstant;
import sylvestre01.vybediaryblog.utils.AppUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sylvestre01.vybediaryblog.utils.AppConstant.*;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private TagRepository tagRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }
    @Override
    public PostResponse addPost(PostPayload postRequest, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User Id is not found"));
        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category id was not found"));
        List<Tag> tags = tagRepository.findAll();
        Post post = new Post();
        post.setBody(postRequest.getBody());
        post.setTitle(postRequest.getTitle());
        post.setCategory(category);
        post.setUser(user);
        post.setTags(tags);

        Post newPost = postRepository.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setTitle(newPost.getTitle());
        postResponse.setBody(newPost.getBody());
        postResponse.setCategory(newPost.getCategory().getName());

        List<String> tagNames = new ArrayList<>(newPost.getTags().size());
        for (Tag tag : newPost.getTags()) {
            tagNames.add(tag.getName());
        }
        postResponse.setTags(tagNames);
        return postResponse;
    }
    @Override
    public PagedResponse<Post> getAllPosts(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }
    @Override
    public PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size) {
        validatePageNumberAndSize(page, size);
        User user = userRepository.getUserByName(username);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Post> posts = postRepository.findByCreatedBy(user.getId(), pageable);

        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }
    @Override
    public PagedResponse<Post> getPostsByCategory(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category id cannot be found"));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Post> posts = postRepository.findByCategory(category.getId(), pageable);

        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }
    @Override
    public PagedResponse<Post> getPostsByTag(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag id could not be found"));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Post> posts = postRepository.findByTagsIn(Collections.singletonList(tag), pageable);

        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }
    @Override
    public Post updatePost(Long id, PostPayload newPostRequest, UserPrincipal currentUser) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post id was not found"));
        Category category = categoryRepository.findById(newPostRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category id was not found"));
        if (post.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            post.setTitle(newPostRequest.getTitle());
            post.setBody(newPostRequest.getBody());
            post.setCategory(category);
            return postRepository.save(post);
        }
        ApiResponse apiResponse = new ApiResponse("You don't have permission to edit this post", LocalDateTime.now());
        throw new UnauthorizedException(apiResponse);
    }
    @Override
    public ApiResponse deletePost(Long id, UserPrincipal currentUser) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post id was not found"));
            if (post.getUser().getId().equals(currentUser.getId())
                    || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
                postRepository.deleteById(id);
                return new ApiResponse("You successfully deleted post", LocalDateTime.now());
            }

            ApiResponse apiResponse = new ApiResponse("You don't have permission to delete this post", LocalDateTime.now());

            throw new UnauthorizedException(apiResponse);
    }
    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post id not found"));
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }
        if (size > AppConstant.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
        }
    }
}
