package sylvestre01.vybediaryblog.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter
@Setter
public class CommentPayload {

    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    public CommentPayload(String body) {
        this.body = body;
    }
}
