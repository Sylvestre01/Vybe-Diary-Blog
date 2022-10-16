package sylvestre01.vybediaryblog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryPayload {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
