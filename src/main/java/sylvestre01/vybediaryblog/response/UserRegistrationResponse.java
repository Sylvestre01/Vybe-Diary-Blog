package sylvestre01.vybediaryblog.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class UserRegistrationResponse {

    private String message;
    private LocalDateTime timeStamp;
}
