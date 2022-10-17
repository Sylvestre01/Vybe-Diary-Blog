package sylvestre01.vybediaryblog.exception;

import org.springframework.http.ResponseEntity;
import sylvestre01.vybediaryblog.response.ApiResponse;

public class ResponseEntityErrorException extends RuntimeException{

    private transient ResponseEntity<ApiResponse> apiResponse;

    public ResponseEntityErrorException(ResponseEntity<ApiResponse> apiResponse) {
        this.apiResponse = apiResponse;
    }

    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }
}
