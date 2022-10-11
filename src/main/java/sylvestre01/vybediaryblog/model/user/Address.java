package sylvestre01.vybediaryblog.model.user;

import lombok.*;
import sylvestre01.vybediaryblog.model.audit.UserDateAudit;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address extends UserDateAudit {

    private String street;

    private String suite;

    private String city;

    private String zipcode;


    @OneToOne(mappedBy = "address")
    private User user;

}
