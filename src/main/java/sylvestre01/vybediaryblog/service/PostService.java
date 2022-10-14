package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.payload.ApiResponse;
import sylvestre01.vybediaryblog.payload.PagedResponse;
import sylvestre01.vybediaryblog.payload.PostRequest;
import sylvestre01.vybediaryblog.payload.PostResponse;

public interface PostService {

    PagedResponse<Post> getAllPosts(int page, int size);

    PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size);

    PagedResponse<Post> getPostsByCategory(Long id, int page, int size);

    PagedResponse<Post> getPostsByTag(Long id, int page, int size);

    Post updatePost(Long id, PostRequest newPostRequest, UserPrincipal currentUser);

    ApiResponse deletePost(Long id, UserPrincipal currentUser);

    PostResponse addPost(PostRequest postRequest, UserPrincipal currentUser);

    Post getPost(Long id);
}
