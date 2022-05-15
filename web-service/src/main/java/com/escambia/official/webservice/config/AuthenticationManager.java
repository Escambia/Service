package com.escambia.official.webservice.config;

import com.escambia.official.webservice.enums.SecurityRoles;
import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.utility.JwtUtility;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * AuthenticationManager
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtility jwtUtility;

    public AuthenticationManager(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        try {
            username = jwtUtility.getUsernameFromToken(authToken);
        } catch (Exception e) {
            return Mono.error(new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "Token 到期，請重新登入") {
            });
        }
        if (username != null && jwtUtility.validateToken(authToken)) {
            Claims claims = jwtUtility.getAllClaimsFromToken(authToken);
            @SuppressWarnings("unchecked")
            List<String> rolesMap = claims.get("role", List.class);
            List<SecurityRoles> roles = new ArrayList<>();
            for (String rolemap : rolesMap) {
                roles.add(SecurityRoles.valueOf(rolemap));
            }

            UserDetails user = new UserDto(
                    claims.get("userId", Integer.class),
                    claims.get("account", String.class),
                    roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
            );


            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }

}
