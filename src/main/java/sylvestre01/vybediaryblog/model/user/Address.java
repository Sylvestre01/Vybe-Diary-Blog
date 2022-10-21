package sylvestre01.vybediaryblog.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.audit.UserBaseClass;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address extends UserBaseClass {

    private String street;

    private String suite;

    private String city;

    private String zipcode;


    @OneToOne(mappedBy = "address")
    private User user;

}
