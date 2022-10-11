package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import sylvestre01.vybediaryblog.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag extends UserDateAudit {

    private String name;

    @ManyToMany
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    private List<Post> posts;

}
