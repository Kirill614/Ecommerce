package kirill.ecommerce.repository;

import kirill.ecommerce.models.EnumRole;
import kirill.ecommerce.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(EnumRole role);
    //Optional<Role> findByRole(String name);
}
