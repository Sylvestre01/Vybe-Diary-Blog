package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.payload.PostPayload;
import sylvestre01.vybediaryblog.response.PostResponse;


public interface PostService {

    PagedResponse<Post> getAllPosts(int page, int size);

    PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size);

    PagedResponse<Post> getPostsByCategory(Long id, int page, int size);

    PagedResponse<Post> getPostsByTag(Long id, int page, int size);

    Post updatePost(Long id, PostPayload newPostRequest, UserPrincipal currentUser);

    ApiResponse deletePost(Long id, UserPrincipal currentUser);

    PostResponse addPost(PostPayload postRequest, UserPrincipal currentUser);

    Post getPost(Long id);
}
