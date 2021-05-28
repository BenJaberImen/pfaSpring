package tn.essat.pfanouveau.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompte;

    private String login;

       private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser",referencedColumnName ="idUser", nullable = false)
    private User user;
    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(name = "compte_role", joinColumns = @JoinColumn(name = "idCompte"), inverseJoinColumns = @JoinColumn(name = "idRole"))
    private Set<Role> roles = new HashSet<>();

    public Compte() {
    }

    public Compte(Integer idCompte, String login, String password, User user, Set<Role> roles) {
        this.idCompte = idCompte;
        this.login = login;
        this.password = password;
        this.user = user;
        this.roles = roles;
    }

    public Compte(String login, String password, User user) {
        this.login = login;
        this.password = password;
        this.user = user;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "idCompte=" + idCompte +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                ", roles=" + roles +
                '}';
    }
}