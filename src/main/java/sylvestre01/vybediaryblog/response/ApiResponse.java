package sylvestre01.vybediaryblog.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private String message;

    private LocalDateTime timeStamp;

    private T data;

    public ApiResponse(String message, LocalDateTime timeStamp, T data) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public ApiResponse(String message, LocalDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
