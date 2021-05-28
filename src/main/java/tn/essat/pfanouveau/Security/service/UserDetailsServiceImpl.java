package tn.essat.pfanouveau.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.essat.pfanouveau.Repsitory.CompteRepisotery;
import tn.essat.pfanouveau.entity.Compte;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CompteRepisotery compteRepisotery;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Compte compte =compteRepisotery.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("utilisateur ne existe pas !!"+login)) ;
        return UserDetailsImpl.build(compte);

    }
}
