package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//classifica como entidade
@Entity

@Table(name = "users")
//cria o user
public class AppUser extends PanacheEntity {

//    indica que a entidade deve ser mapeada em coluna
    @Column (unique = true)
    public String username;
    public String password;
    public String role;

}
