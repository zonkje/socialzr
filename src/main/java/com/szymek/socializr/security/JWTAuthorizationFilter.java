package com.szymek.socializr.security;

import com.szymek.socializr.common.SocialzrConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(SocialzrConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SocialzrConstants.TOKEN_PREFIX, "");

        Claims userAndRoles = Jwts
                .parserBuilder()
                .setSigningKey(SocialzrConstants.JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String user = userAndRoles.getSubject();

        List<Map<String, String>> authorities = (List<Map<String, String>>) userAndRoles.get("authorities");

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.forEach(
                authority -> {
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()));
                }
        );

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        simpleGrantedAuthorities
                ));
        filterChain.doFilter(request, response);
    }
}
