package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import sylvestre01.vybediaryblog.model.audit.BaseClass;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category extends UserBaseClass {

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Post> posts;
}
