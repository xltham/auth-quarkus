package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.service.GenerateToken;
import org.mindrot.jbcrypt.BCrypt;

// registro no banco de dados e verificar se o usuario existe

@ApplicationScoped
public class UserService {

    public User findByUsername (String username){
        return User.find("username", username).firstResult();
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateToken(String username, String role){
        return GenerateToken.generateToken(username, role);
    }


}


