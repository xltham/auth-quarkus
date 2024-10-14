package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Role;

import java.util.Set;

@ApplicationScoped
public class RoleService {

    public Role findByRoleName (Set<Role> roleName){
        return Role.find("roleName", roleName).firstResult();
    }

}
