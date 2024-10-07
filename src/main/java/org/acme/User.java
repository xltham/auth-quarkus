package org.acme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

//classifica como entidade
@Entity

//cria o user
public class User {

    //indica que a entidade deve ser mapeada em coluna
    @Column
    public String username;
    public String password;
    public String role;

}
