package py.edu.facitec.ventas.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Usuario;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "claveSuperSecretaMuyLargaQueTengaAlMenos32Caracteres!";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 horas

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getNombre())
                .claim("id", usuario.getId())
                .claim("rol", usuario.getRol().name())
                .claim("email", usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return getClaims(token).get("rol", String.class);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String rol = claims.get("rol", String.class);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.singletonList(authority)
        );
    }
}