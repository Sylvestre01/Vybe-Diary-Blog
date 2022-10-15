package sylvestre01.vybediaryblog.serviceimpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.exception.UnauthorizedException;
import sylvestre01.vybediaryblog.model.Tag;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.payload.ApiResponse;
import sylvestre01.vybediaryblog.payload.PagedResponse;
import sylvestre01.vybediaryblog.repository.TagRepository;
import sylvestre01.vybediaryblog.service.TagService;
@Service

public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public PagedResponse<Tag> getAllTags(int page, int size) {
        return null;
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("tag id cannot be found"));
    }

    @Override
    public Tag addTag(Tag tag, UserPrincipal currentUser) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("tag id could not be found"));
        if (tag.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            tag.setName(newTag.getName());
            return tagRepository.save(tag);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this tag");

        throw new UnauthorizedException(apiResponse);

    }

    @Override
    public ApiResponse deleteTag(Long id, UserPrincipal currentUser) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("tag id could not be found"));
        if (tag.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            tagRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted tag");
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this tag");

        throw new UnauthorizedException(apiResponse);
    }
}
