package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Set;

// registro no banco de dados e verificar se o usuario existe
@ApplicationScoped
public class UserService {

    public  User findByUsername (String username){
        return User.find("username", username).firstResult();
    }

    public  User findByCpf (String cpf){

        return User.find("cpf", cpf).firstResult();
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateToken(String username, Set<String> roles){
        return GenerateToken.generateToken(username, roles);
    }



}


