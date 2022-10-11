package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import sylvestre01.vybediaryblog.model.audit.UserDateAudit;
import sylvestre01.vybediaryblog.model.user.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Like extends UserDateAudit {

    private boolean isLiked;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id" , referencedColumnName = "id")
    private Post post;

}
