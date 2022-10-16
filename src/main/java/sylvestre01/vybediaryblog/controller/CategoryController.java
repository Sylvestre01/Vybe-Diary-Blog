package sylvestre01.vybediaryblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sylvestre01.vybediaryblog.Security.CurrentUser;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.UnauthorizedException;
import sylvestre01.vybediaryblog.model.Category;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.service.CategoryService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public PagedResponse<Category> getAllCategories(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
        return categoryService.getAllCategories(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, @CurrentUser UserPrincipal currentUser) {
        return categoryService.addCategory(category, currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(name = "id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Category> UpdateCategory(@PathVariable(name = "id") Long id,
                                                   @Valid @RequestBody Category category,
                                                   @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return categoryService.updateCategory(id, category, currentUser);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return categoryService.deleteCategory(id, currentUser);
}

}
