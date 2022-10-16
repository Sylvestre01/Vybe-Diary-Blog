package sylvestre01.vybediaryblog.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.BlogapiException;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.payload.CommentPayload;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.repository.CommentRepository;
import sylvestre01.vybediaryblog.repository.PostRepository;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.service.CommentService;
import sylvestre01.vybediaryblog.utils.AppUtils;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PagedResponse<Comment> getAllComments(Long postId, int page, int size) {

        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        return new PagedResponse<>(comments.getContent(), comments.getNumber(), comments.getSize(),
                comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
    }

    @Override
    public Comment addComment(CommentPayload commentRequest, Long postId, UserPrincipal currentUser) {
        User user = userRepository.getUser(currentUser);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Id was not found"));
        Comment comment = new Comment(commentRequest.getBody());
        comment.setUser(user);
        comment.setPost(post);
        comment.setName(currentUser.getUsername());
        comment.setEmail(currentUser.getEmail());
        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Long postId, Long id) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post id was not found"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment id was not found"));
        if (comment.getPost().getId().equals(post.getId())) {
            return comment;
        } else {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
    }

    @Override
    public Comment updateComment(Long postId, Long id, CommentPayload commentRequest, UserPrincipal currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Id was not found"));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment id was not found"));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        if (comment.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            comment.setBody(commentRequest.getBody());
            return commentRepository.save(comment);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "You don't have permission to update this comment");
    }

    @Override
    public ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post id was not found"));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment id was not found"));

        if (!comment.getPost().getId().equals(post.getId())) {
            return new ApiResponse("Comment does not belong to post", LocalDateTime.now());
        }

        if (comment.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            commentRepository.deleteById(comment.getId());
            return new ApiResponse("You successfully deleted comment", LocalDateTime.now());
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "You don't have permission to delete this comment");
    }
}
