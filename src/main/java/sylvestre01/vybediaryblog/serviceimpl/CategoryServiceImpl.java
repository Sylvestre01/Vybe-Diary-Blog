package sylvestre01.vybediaryblog.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.exception.UnauthorizedException;
import sylvestre01.vybediaryblog.model.Category;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.repository.CategoryRepository;
import sylvestre01.vybediaryblog.service.CategoryService;
import sylvestre01.vybediaryblog.utils.AppUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PagedResponse<Category> getAllCategories(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Category> categories = categoryRepository.findAll(pageable);

        List<Category> content = categories.getNumberOfElements() == 0 ? Collections.emptyList() : categories.getContent();

        return new PagedResponse<>(content, categories.getNumber(), categories.getSize(), categories.getTotalElements(),
                categories.getTotalPages(), categories.isLast());
    }

    @Override
    public ResponseEntity<Category> getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category id was not found"));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser) {
        Category newCategory = categoryRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
            throws UnauthorizedException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category id was not found"));
        if (category.getCreateDate().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            category.setName(newCategory.getName());
            Category updatedCategory = categoryRepository.save(category);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        }

        throw new UnauthorizedException("You don't have permission to edit this category");
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category id was not found"));
        if (category.getCreateDate().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse("You successfully deleted category", LocalDateTime.now()), HttpStatus.OK);
        }
        throw new UnauthorizedException("You don't have permission to delete this category");
            }
    }


