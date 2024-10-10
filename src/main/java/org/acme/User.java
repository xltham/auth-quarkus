package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
        String CPF;
     String role;


     //  constructor vazio
     public User (){}

    public User(String username, String password,String CPF, String role) {
        this.username = username;
        this.password = BcryptUtil.bcryptHash(password);
        this.CPF = CPF;
        this.role = role;
    }


}
