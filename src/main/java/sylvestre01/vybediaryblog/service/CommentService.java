package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.payload.ApiResponse;
import sylvestre01.vybediaryblog.payload.CommentRequest;
import sylvestre01.vybediaryblog.payload.PagedResponse;

public interface CommentService {
    PagedResponse<Comment> getAllComments(Long postId, int page, int size);

    Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

    Comment getComment(Long postId, Long id);

    Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

    ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);

}
