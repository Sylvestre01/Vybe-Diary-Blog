package sylvestre01.vybediaryblog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.UnauthorizedException;
import sylvestre01.vybediaryblog.model.Category;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;

public interface CategoryService {

    PagedResponse<Category> getAllCategories(int page, int size);

    ResponseEntity<Category> getCategory(Long id);

    ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

    ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
            throws UnauthorizedException;

    ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException;

}
