package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import sylvestre01.vybediaryblog.model.audit.BaseClass;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;
import sylvestre01.vybediaryblog.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends UserBaseClass {

    private String title;

    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;
}
