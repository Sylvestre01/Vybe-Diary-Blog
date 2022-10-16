package sylvestre01.vybediaryblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sylvestre01.vybediaryblog.Security.CurrentUser;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.Tag;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.service.TagService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Tag>> getAllTags(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {

        PagedResponse<Tag> response = tagService.getAllTags(page, size);

        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Tag> addTag(@Valid @RequestBody Tag tag, @CurrentUser UserPrincipal currentUser) {
        Tag newTag = tagService.addTag(tag, currentUser);

        return new ResponseEntity< >(newTag, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable(name = "id") Long id) {
        Tag tag = tagService.getTag(id);

        return new ResponseEntity< >(tag, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Tag> updateTag(@PathVariable(name = "id") Long id, @Valid @RequestBody Tag tag, @CurrentUser UserPrincipal currentUser) {

        Tag updatedTag = tagService.updateTag(id, tag, currentUser);

        return new ResponseEntity< >(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = tagService.deleteTag(id, currentUser);

        return new ResponseEntity< >(apiResponse, HttpStatus.OK);
    }



}
