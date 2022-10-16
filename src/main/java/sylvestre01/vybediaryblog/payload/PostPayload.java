package sylvestre01.vybediaryblog.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
public class PostPayload {

    @NotBlank
    @Size(min = 10)
    private String title;

    @NotBlank
    @Size(min = 50)
    private String body;

    @NotNull
    private Long categoryId;

    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    //    public void setTags(List<String> tags) {
//
//        if (tags == null) {
//            this.tags = null;
//        } else {
//            this.tags = Collections.unmodifiableList(tags);
//        }
//    }
}
