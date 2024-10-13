package org.acme.permission;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/permission")
public class PermissionResource {

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String admin() {
        return "Hello, Admin!";
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public String me() {
        return identity.getPrincipal().getName();
    }
}
