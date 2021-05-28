package tn.essat.pfanouveau.Security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.essat.pfanouveau.entity.Compte;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID=1L;

    @JsonIgnore
    private final String password;
    private final Integer id;
    private final String login;
    private final Collection<? extends GrantedAuthority> authorities;
    private String firstName;
    private String lastName;

    public UserDetailsImpl(Integer id, String login, String firstName, String lastName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.authorities = authorities;
    }
    public static UserDetailsImpl build(Compte compte) {
        List<GrantedAuthority> authorities = compte.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl( compte.getIdCompte(),compte.getPassword(), compte.getLogin(), compte.getUser().getFirstName(), compte.getUser().getLastName(), authorities);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl compte = (UserDetailsImpl) o;
        return Objects.equals(id,compte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,password, login, firstName, lastName, authorities);
    }


}
