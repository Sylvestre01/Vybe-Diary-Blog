package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
