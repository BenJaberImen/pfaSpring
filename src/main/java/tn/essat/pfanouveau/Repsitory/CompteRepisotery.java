package tn.essat.pfanouveau.Repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.essat.pfanouveau.entity.Compte;


import java.util.Optional;

public interface CompteRepisotery extends JpaRepository<Compte, Integer> {


    Optional <Compte> findByLogin(String login);
    Boolean existsByLogin(String login);
}
