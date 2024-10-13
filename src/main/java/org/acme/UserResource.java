package org.acme;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
//import org.acme.entity.Role;
import org.acme.service.AESUtil;
import org.acme.service.UserService;

@Path("/users")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;
    SecurityIdentity identity;

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO userDTO) throws Exception {

        var newUser = new User(userDTO.username, userDTO.password, userDTO.cpf, userDTO.role );
        User.persist(newUser);
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    public Response login(UserDTO userDTO) {
        User user = userService.findByUsername(userDTO.username);
        if (user != null && userService.verifyPassword(userDTO.password, user.password)) {

                String token = userService.generateToken(user.username, user.role);
                return Response.ok(token).build();
        }

        return Response.status(401).entity("Credenciais invalidas").build() ;
    }

    @POST
    @Path("/finder")
    public Response finder(UserDTO userDTO) throws Exception {
        User user = userService.findByUsername(userDTO.username);
        if (user != null){
            user.cpf = AESUtil.decrypt(user.cpf);
            return Response.ok(user.username + " -> " + user.cpf).build();
        }
        return Response.status(401).entity("Credenciais invalidas").build() ;
    }

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public String me() {
        return identity.getPrincipal().getName();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String admin() {
        return "Hello, Admin!";
    }
}
