package sylvestre01.vybediaryblog.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.audit.BaseClass;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;
import sylvestre01.vybediaryblog.model.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends UserBaseClass {

    @NotBlank
    @Size(min = 4, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Email
    @NotBlank
    @Size(min = 4, max = 50)
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







