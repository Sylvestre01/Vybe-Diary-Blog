package sylvestre01.vybediaryblog.response;

import lombok.Data;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.UserDateAuditPayload;

@Data
public class AlbumResponse extends UserDateAuditPayload {

    private Long id;

    private String title;

    private User user;

    private String photo;

}
