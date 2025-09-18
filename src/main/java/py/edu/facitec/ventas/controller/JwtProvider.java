package py.edu.facitec.ventas.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import py.edu.facitec.ventas.entity.Usuario;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final String SECRET = "claveSuperSecretaMuyLargaQueTengaAlMenos32Caracteres!";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 horas

    // Genera el token incluyendo el rol
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getNombre())
                .claim("id", usuario.getId())
                .claim("rol", usuario.getRol()) // ej: "ADMIN"
                .claim("email", usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtiene los claims del token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ⚡ Nuevo método: convertir token en Authentication para Spring Security
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        // Obtenemos el rol y le agregamos ROLE_ para Spring
        String rol = claims.get("rol", String.class);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.singletonList(authority)
        );
    }
}
