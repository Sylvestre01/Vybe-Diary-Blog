package sylvestre01.vybediaryblog.payload;

import lombok.Data;
import sylvestre01.vybediaryblog.model.Photo;
import sylvestre01.vybediaryblog.model.user.User;

import java.util.List;
@Data
public class AlbumRequest extends UserDateAuditPayload {

    private Long id;

    private String title;

    private User user;

    private String photo;
}
