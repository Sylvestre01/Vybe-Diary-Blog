package sylvestre01.vybediaryblog.payload;

import lombok.Data;

@Data
public abstract class UserDateAuditPayload extends DateAuditPayload{

    private Long createdBy;

    private Long updatedBy;

}
