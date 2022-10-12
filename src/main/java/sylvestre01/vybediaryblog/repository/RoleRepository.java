package sylvestre01.vybediaryblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sylvestre01.vybediaryblog.model.role.Role;
import sylvestre01.vybediaryblog.model.role.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}
