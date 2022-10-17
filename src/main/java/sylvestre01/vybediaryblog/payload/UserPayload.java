package sylvestre01.vybediaryblog.payload;

import lombok.Getter;
import lombok.Setter;
import sylvestre01.vybediaryblog.model.user.Address;
import sylvestre01.vybediaryblog.model.user.User;

@Getter
@Setter
public class UserPayload {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private Address address;

    private String phone;

    private String website;

    public User getUserFromPayload() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setPhone(phone);
        user.setWebsite(website);

        return new User();
    }
}
