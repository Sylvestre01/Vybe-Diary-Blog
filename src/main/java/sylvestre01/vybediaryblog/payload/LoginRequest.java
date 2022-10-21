package sylvestre01.vybediaryblog.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
