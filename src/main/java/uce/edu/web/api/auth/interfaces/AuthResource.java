package uce.edu.web.api.auth.interfaces;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.auth.models.Usuario; // Importante: importar tu entidad

import java.time.Instant;
import java.util.Set;

@Path("/auth")
public class AuthResource {

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(
            @QueryParam("user")  String user,
            @QueryParam("password")  String password,
            @QueryParam("rol") String rol) {
        Usuario usuarioEncontrado = Usuario.find("username", user).firstResult();

        if (usuarioEncontrado != null && usuarioEncontrado.password.equals(password)) {

            String issuer = "matricular-auth";
            long ttl = 3600;
            Instant now = Instant.now();
            Instant exp = now.plusSeconds(ttl);

            String jwt = Jwt.issuer(issuer)
                    .subject(user)
                    .groups(rol.toLowerCase())
                    .issuedAt(now)
                    .expiresAt(exp)
                    .sign();

            return Response.ok(new TokenResponse(jwt,rol.toLowerCase())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales incorrectas").build();
        }
    }


    public static class TokenResponse {
        public String token;
        public String role;
        public TokenResponse() {
        }

        public TokenResponse(String accessToken, String role) {
            this.token = accessToken;
            this.role = role.toLowerCase();
        }
    }
}