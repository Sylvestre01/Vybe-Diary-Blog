package sylvestre01.vybediaryblog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sylvestre01.vybediaryblog.model.user.Address;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilePayload {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Instant joinedAt;
    private String email;
    private Address address;
    private String phone;
    private String website;

    public UserProfilePayload(Long id, String username, String firstName, String lastName, Instant now, String email, Address address, String phone, String website, Long postCount) {
    }
}
