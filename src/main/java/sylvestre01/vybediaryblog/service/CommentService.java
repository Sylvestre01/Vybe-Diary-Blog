package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.payload.CommentPayload;
import sylvestre01.vybediaryblog.response.PagedResponse;

public interface CommentService {
    PagedResponse<Comment> getAllComments(Long postId, int page, int size);

    Comment addComment(CommentPayload commentRequest, Long postId, UserPrincipal currentUser);

    Comment getComment(Long postId, Long id);

    Comment updateComment(Long postId, Long id, CommentPayload commentRequest, UserPrincipal currentUser);

    ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);

}
