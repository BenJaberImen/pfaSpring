package tn.essat.pfanouveau.Repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.essat.pfanouveau.entity.User;

public interface IUserRepo extends JpaRepository<User,Integer> {
}
