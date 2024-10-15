package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.acme.auth.UserDTO;
import org.acme.entity.Role;
import org.acme.entity.User;
import org.acme.service.RoleService;
import org.acme.service.UserService;

@Path("/role")
public class RoleResource {

    @Inject
    RoleService roleService;
    @Inject
    UserService userService;


    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO userDTO) throws Exception{

        var newRole = new Role(userDTO.role);
        Role.persist(newRole);
        return Response.status(201).build();
    }

    @PUT //update
    @Path("/add/{username}/roles/{roleName}")
    @Transactional
    public Response addRoleToUser(@PathParam("username") String username, @PathParam("roleName") String roleName) {
        User user = userService.findByUsername(username);
        Role role = roleService.findByRoleName(roleName);

        if (user == null || role == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.roles.add(role);
        return Response.ok(user.username + " -> " + role.roleName).build();
    }

    @PUT //update
    @Path("/remove/{username}/roles/{roleName}")
    @Transactional
    public Response removeRoleToUser(@PathParam("username") String username, @PathParam("roleName") String roleName) {
        User user = userService.findByUsername(username);
        Role role = roleService.findByRoleName(roleName);

        if (user == null || role == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.roles.remove(role);

        return Response.ok(user.username + " -> " + role.roleName).build();
    }


}
