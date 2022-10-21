package sylvestre01.vybediaryblog.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.Album;
import sylvestre01.vybediaryblog.model.Comment;
import sylvestre01.vybediaryblog.model.Post;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;
import sylvestre01.vybediaryblog.model.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    @JsonIgnore
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


    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Album> albums;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User(Long id, LocalDateTime createDate, LocalDateTime updateDate,
                Long createdBy, Long updatedBy, String firstName, String lastName,
                String username, String password, String email, String phone,
                String website, Role role, Address address, List<Album> albums,
                List<Post> posts, List<Comment> comments) {
        super(id, createDate, updateDate, createdBy, updatedBy);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.role = role;
        this.address = address;
        this.albums = albums;
        this.posts = posts;
        this.comments = comments;
    }
}







