package sylvestre01.vybediaryblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.audit.BaseClass;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photos")
public class Photo extends UserBaseClass {

    private String title;

    private String url;

    private String thumbnailUrl;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private Album album;

}
