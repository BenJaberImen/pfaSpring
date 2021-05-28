package tn.essat.pfanouveau.service;

import tn.essat.pfanouveau.entity.Compte;

public interface CompteService {
    Boolean existBylogin(String login);
    void addCompte(Compte compte);


}
