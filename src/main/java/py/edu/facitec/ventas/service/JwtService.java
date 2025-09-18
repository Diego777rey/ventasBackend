package py.edu.facitec.ventas.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;

@Service
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clave secreta
    private final long expiration = 1000 * 60 * 60 * 2; // 2 horas

    // Generar token
    public String generateToken(String username, String rol) {
        return Jwts.builder()
                .setSubject(username)
                .claim("rol", rol) // ej: "ADMIN"
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // Validar token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Obtener usuario del token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Obtener rol del token
    public String getRoleFromToken(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol");
    }

    // ⚡ Nuevo: obtener Authentication con rol para Spring Security
    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        String rol = getRoleFromToken(token);

        // Agregar prefijo ROLE_ para que Spring Security lo reconozca
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(authority)
        );
    }
}
