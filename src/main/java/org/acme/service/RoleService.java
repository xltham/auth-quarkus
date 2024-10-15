package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Role;

@ApplicationScoped
public class RoleService {

    public Role findByRoleName (String roleName){
        return Role.find("roleName", roleName).firstResult();
    }



}
