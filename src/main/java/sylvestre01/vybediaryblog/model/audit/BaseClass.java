package sylvestre01.vybediaryblog.model.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
