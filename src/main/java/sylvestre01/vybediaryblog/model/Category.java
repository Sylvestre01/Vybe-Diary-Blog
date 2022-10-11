package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import sylvestre01.vybediaryblog.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category extends UserDateAudit {

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Post> posts;
}
