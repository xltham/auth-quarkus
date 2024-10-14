package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Roles;
import jakarta.persistence.*;
import me.yanaga.opes.Cpf;
import org.acme.entity.Role;
import org.acme.service.AESUtil;

import java.util.Set;

//classifica como entidade para o banco de dados
@Entity

//cria o user
@Table(name = "users")
public class User extends PanacheEntity {

//    indica que a entidade deve ser mapeada em coluna
    @Column (unique = true)
     String username;
     String password;
     @Column (unique = true)
     String cpf;
//     String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "rolename", referencedColumnName = "roleName")
    )
    @Roles
    public Set<Role> roles;

     // CONSTRUTOR VAZIO É LEI
     public User (){}
     // CONSTRUTOR LEMBRA DISSO BURRO
     public User(String username, String password, String cpf) throws Exception {

        if (!CpfValidator(cpf)) {
            throw new IllegalArgumentException("Cpf invalido");
        }
        this.username = username;
        this.password = BcryptUtil.bcryptHash(password);
        this.cpf =  AESUtil.encrypt(cpf);
     }

    private boolean CpfValidator(String cpf) {
        try {
            Cpf.of(cpf);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
