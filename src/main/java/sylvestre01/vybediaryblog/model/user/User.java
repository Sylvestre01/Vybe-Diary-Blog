package sylvestre01.vybediaryblog.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.audit.DateAudit;
import sylvestre01.vybediaryblog.model.audit.UserDateAudit;
import sylvestre01.vybediaryblog.model.role.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends UserDateAudit {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String phone;

    private String website;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Album> albums;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

}







