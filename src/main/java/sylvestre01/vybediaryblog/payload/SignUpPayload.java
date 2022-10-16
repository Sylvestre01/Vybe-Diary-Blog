package sylvestre01.vybediaryblog.payload;

import lombok.Data;
import sylvestre01.vybediaryblog.model.role.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
@Data
public class SignUpRequest {


    @Size(min = 4, max = 40)
    private String firstName;

    @Size(min = 4, max = 40)
    private String lastName;


    @Size(min = 3, max = 15)
    private String username;


    @Size(max = 40)
    @Email
    private String email;


    @Size(min = 6, max = 20)
    private String password;

}
