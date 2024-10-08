package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

//classifica como entidade
@Entity

@Table(name = "users")
//cria o user
public class User extends PanacheEntity {

//    indica que a entidade deve ser mapeada em coluna
    @Column (unique = true)
     String username;
     String password;
     String role;

     //  constructor vazio
     public User (){}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = BcryptUtil.bcryptHash(password);
        this.role = role;
    }


}
