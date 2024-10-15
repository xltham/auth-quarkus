package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
import org.acme.entity.User;
import org.acme.entity.User;
import org.acme.service.AESUtil;
import org.acme.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Path("/login")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
    @Inject
    UserService userService;

    @POST
    public Response login(UserDTO userDTO) throws Exception {
        userDTO.cpf = AESUtil.encrypt(userDTO.cpf);
        User user = userService.findByCpf(userDTO.cpf);
        if (user != null && userService.verifyPassword(userDTO.password, user.password)) {
            Set<String> roles = new HashSet<>();
            user.roles.forEach(role -> roles.add(role.roleName));
            String token = userService.generateToken(user.username, roles);
            return Response.ok(roles + " token:\n" + token).build();
        }

        return Response.status(401).entity("Credenciais invalidas").build() ;
    }
}
