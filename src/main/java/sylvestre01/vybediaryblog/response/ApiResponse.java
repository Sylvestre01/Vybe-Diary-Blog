package sylvestre01.vybediaryblog.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse<U> implements Serializable {


    private String message;

    private LocalDateTime timeStamp;

    private HttpStatus status;

    public ApiResponse(String message, LocalDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public ApiResponse(String message, LocalDateTime timeStamp, HttpStatus status) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.status = status;
    }
}
