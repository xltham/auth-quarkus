package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
import org.acme.entity.Role;

@Path("/role")
public class RoleResource {

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO userDTO) throws Exception{

        var newRole = new Role(userDTO.role);
        Role.persist(newRole);
        return Response.status(201).build();
    }


}
