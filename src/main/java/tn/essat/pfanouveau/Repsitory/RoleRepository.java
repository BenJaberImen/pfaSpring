package tn.essat.pfanouveau.Repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.essat.pfanouveau.entity.ERole;
import tn.essat.pfanouveau.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role ,Integer> {


    Optional<Role> findByRole(ERole role);
}

