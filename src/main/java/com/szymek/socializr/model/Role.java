package com.szymek.socializr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Role {

    USER(Collections.singletonList("USER")),
    ADMIN(Arrays.asList("ADMIN", "USER"));

    private Collection<String> roles;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
