package org.acme;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
//import org.acme.entity.Role;
import org.acme.entity.Role;
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
    RoleService roleService; ;
    @Inject
    SecurityIdentity identity;
//    User user;

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO userDTO) throws Exception {

        var newUser = new User(userDTO.username, userDTO.password, userDTO.cpf);

        User.persist(newUser);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{username}/roles/{roleName}")
    @Transactional
    public Response assignRoleToUser(@PathParam("username") String username, @PathParam("roleName") Set<Role> roleName) {
        User user = userService.findByUsername(username);
        Role role = roleService.findByRoleName(roleName);

        if (user == null || role == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.roles.add(role);
        return Response.ok(user + " -> " + role).build();
    }


    @POST
    @Path("/login")
    public Response login(UserDTO userDTO) throws Exception {
        userDTO.cpf = AESUtil.encrypt(userDTO.cpf);
        User user = userService.findByCpf(userDTO.cpf);
        if (user != null && userService.verifyPassword(userDTO.password, user.password)) {
            Set<String> roles = new HashSet<>();
            user.roles.forEach(role -> roles.add(role.roleName));
            String token = userService.generateToken(user.username, roles);
                return Response.ok(user.roles + " token:\n" + token).build();
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
//
//    @GET
//    @Path("/me")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String me() {
//        return identity.getPrincipal().getName();
//    }
//
//    @GET
//    @Path("/admin")
//    @RolesAllowed("admin")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String admin() {
//        return "Hello, Admin!";
//    }
}
