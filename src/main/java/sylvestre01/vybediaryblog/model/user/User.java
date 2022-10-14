package sylvestre01.vybediaryblog.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.audit.UserDateAudit;
import sylvestre01.vybediaryblog.model.role.Role;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;


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







