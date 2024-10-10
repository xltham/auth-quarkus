package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
import org.acme.service.AESUtil;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/users")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO userDTO) throws Exception {

        userDTO.CPF = AESUtil.encrypt(userDTO.CPF);
        var newUser = new User(userDTO.username, userDTO.password, userDTO.CPF, userDTO.role);
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
            user.CPF = AESUtil.decrypt(user.CPF);
            return Response.ok(user.username + " -> " + user.CPF).build();
        }
        return Response.status(401).entity("Credenciais invalidas").build() ;
    }
}
