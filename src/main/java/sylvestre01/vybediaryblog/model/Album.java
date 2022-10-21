package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;
import sylvestre01.vybediaryblog.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "albums")
public class Album extends UserBaseClass {


    @Column(name = "title")
    private String title;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "album")
    private List<Photo> photo;

}
