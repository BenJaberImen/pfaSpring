package tn.essat.pfanouveau.service;

import org.springframework.beans.factory.annotation.Autowired;
import tn.essat.pfanouveau.Repsitory.CompteRepisotery;
import tn.essat.pfanouveau.entity.Compte;

public class CompteServiceImpl implements CompteService {

@Autowired
    CompteRepisotery compteRepisotery;
    @Override
    public Boolean existBylogin(String login) {
        return compteRepisotery.existsByLogin(login);
    }

    @Override
    public void addCompte(Compte compte) {
        compteRepisotery.save(compte);

    }
}
