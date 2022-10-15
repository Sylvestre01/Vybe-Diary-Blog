package sylvestre01.vybediaryblog.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class BaseClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    public String getCreateDate() {
        return createDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a")).toUpperCase();
    }

    public String getUpdateDate() {
        return updateDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a")).toUpperCase();
    }
}
