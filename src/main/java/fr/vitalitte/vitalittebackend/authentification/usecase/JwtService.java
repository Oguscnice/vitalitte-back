package fr.vitalitte.vitalittebackend.authentification.usecase;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

@Service
public class JwtService {

    @Value("${vitalitte-project.app.jwtSecret}")
    private String secretKey;

    public boolean isRoleAdminAndTokenNotExpired() {
        return isRoleVerifyAndTokenNotExpired("ROLE_ADMIN");
    }

    public boolean isRoleUserAndTokenNotExpired() {
        return isRoleVerifyAndTokenNotExpired("ROLE_USER");
    }

    private boolean isRoleVerifyAndTokenNotExpired(String role) {
        return hasRole(role) && isTokenNotExpired();
    }

    private boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (role.equals(authority.getAuthority()) || role.equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private boolean isTokenNotExpired() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7); // Retire le préfixe "Bearer "
        }

        Claims claims = getClaimsFromToken(bearerToken);
        return claims.getExpiration().after(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
