package sylvestre01.vybediaryblog.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse implements Serializable {

    private Boolean success;

    private String message;

    private HttpStatus status;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
