package tn.essat.pfanouveau.entity;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @Enumerated(EnumType.STRING)
    private ERole role;

    public Role() {
    }

    public Role(Integer id, ERole role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idRole) {
        this.id = idRole;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + id +
                ", role=" + role +
                '}';
    }
}
