package sylvestre01.vybediaryblog.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.exception.UnauthorizedException;
import sylvestre01.vybediaryblog.model.Tag;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.response.ApiResponse;
import sylvestre01.vybediaryblog.response.PagedResponse;
import sylvestre01.vybediaryblog.repository.TagRepository;
import sylvestre01.vybediaryblog.service.TagService;
import sylvestre01.vybediaryblog.utils.AppUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;
    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public PagedResponse<Tag> getAllTags(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "CreatedAt");
        Page<Tag> tags = tagRepository.findAll(pageable);
        List<Tag> content = tags.getNumberOfElements() == 0 ? Collections.emptyList() : tags.getContent();
        return new PagedResponse<>(content, tags.getNumber(), tags.getSize(),
                tags.getTotalElements(), tags.getTotalPages(), tags.isLast());
    }
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Tag id cannot be found"));
    }

    @Override
    public Tag addTag(Tag tag, UserPrincipal currentUser) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag id could not be found"));
        if (tag.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            tag.setName(newTag.getName());
            return tagRepository.save(tag);
        }
        ApiResponse apiResponse = new ApiResponse("You don't have permission to edit this tag", LocalDateTime.now());
        throw new UnauthorizedException(apiResponse);

    }
    @Override
    public ApiResponse deleteTag(Long id, UserPrincipal currentUser) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag id could not be found"));
        if (tag.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
            tagRepository.deleteById(id);
            return new ApiResponse("You successfully deleted tag", LocalDateTime.now());
        }

        ApiResponse apiResponse = new ApiResponse("You don't have permission to delete this tag", LocalDateTime.now());

        throw new UnauthorizedException(apiResponse);
    }
}
