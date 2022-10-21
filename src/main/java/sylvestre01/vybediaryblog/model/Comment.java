package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;
import sylvestre01.vybediaryblog.model.user.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends UserBaseClass {

    @NotBlank
    @Size(min = 4, max = 50)
    private String name;

    @NotBlank
    @Email
    @Size(min = 4, max = 50)
    private String email;

    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Comment(@NotBlank @Size(min = 10, message = "Comment body must be minimum 10 characters")String body) {
        this.body = body;
    }
}
