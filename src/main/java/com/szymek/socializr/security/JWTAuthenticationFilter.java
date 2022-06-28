package com.szymek.socializr.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            SignInRequest signInRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), SignInRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    signInRequest.getUsername(),
                    signInRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        Instant expirationDate = Instant.now().plus(SocialzrConstants.TOKEN_EXPIRATION_TIME_DAYS, ChronoUnit.DAYS);

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expirationDate))
                .signWith(Keys.hmacShaKeyFor(SocialzrConstants.JWT_SECRET.getBytes()))
                .compact();

        response.addHeader("Access-Control-Expose-Headers", HttpHeaders.AUTHORIZATION);
        response.addHeader(HttpHeaders.AUTHORIZATION, SocialzrConstants.TOKEN_PREFIX + token);
        User user = (User) authResult.getPrincipal();
        response.getWriter()
                .write("{" +
                        "\"id\":\"" + user.getId() + "\"," +
                        "\"username\":\"" + user.getUsername() + "\"," +
                        "\"role\":\"" + user.getRole() + "\"," +
                        "\"tokenExpirationDate\":\"" + formatter.format(expirationDate) + "\"" +
                        "}");
    }
}
