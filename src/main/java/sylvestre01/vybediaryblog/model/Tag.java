package sylvestre01.vybediaryblog.model;

import lombok.*;
import sylvestre01.vybediaryblog.model.audit.BaseClass;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tags")
public class Tag extends UserBaseClass {

    private String name;

    @ManyToMany
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    private List<Post> posts;


}
