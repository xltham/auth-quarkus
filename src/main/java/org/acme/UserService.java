package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

// registro no banco de dados e verificar se o usuario existe

@ApplicationScoped
public class UserService {

    @Transactional
    public void registerUser(String username, String password, String role){
        User user = new User();
        user.username = username;
        user.password = BCrypt.hashpw(password, BCrypt.gensalt());
        user.role = role;
        user.persist();
    }

    public User findByUsername (String username){
        return User.find("username", username).firstResult();
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }


}


