package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/users")
@Produces("application/json")
public class UserResource {
    @Inject
    UserService userService;
    JsonWebToken jwt;

    @POST
    @Path("/register")
    public Response register(UserDTO userDTO) {
        userService.registerUser(userDTO.username, userDTO.password, userDTO.role);
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    public Response login(UserDTO userDTO) {
        User user = userService.findByUsername(userDTO.username);
        if (user != null && userService.verifyPassword(userDTO.password, user.password)) {
            return Response.ok().build();
        }
        return Response.status(401).entity("Credenciais invalidas").build();
    }
}
