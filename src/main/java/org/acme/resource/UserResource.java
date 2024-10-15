package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
import org.acme.entity.Role;
import org.acme.entity.User;
import org.acme.service.AESUtil;
import org.acme.service.RoleService;
import org.acme.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Path("/users")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;
    @Inject
    RoleService roleService;

    @POST //Create
    @Path("/register")
    @Transactional
    public Response register(UserDTO userDTO) throws Exception {

        var newUser = new User(userDTO.username, userDTO.password, userDTO.cpf);

        User.persist(newUser);
        return Response.status(201).build();
    }
    
    @POST
    @Path("/cpfFinder")
    public Response finder(UserDTO userDTO) throws Exception {
        User user = userService.findByUsername(userDTO.username);
        if (user != null) {
            user.cpf = AESUtil.decrypt(user.cpf);
            return Response.ok(user.username + " -> " + user.cpf).build();
        }
        return Response.status(401).entity("Credenciais invalidas").build();
    }
}

