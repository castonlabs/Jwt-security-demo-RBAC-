package com.zyratechnologies.jwtrolebasedproject.model;

import com.zyratechnologies.jwtrolebasedproject.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  String id;
    @NotNull(message = "this field can not be null")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "this field can not be null")
    @Column(name =   " last_name   " )
    private String lastName;

    @NotNull(message = "this field can not be null")
    @Column(name =   " username  " )
    private String username;

    @NotNull(message = "this field can not be null")
    @Column(name =   " password " )
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "this field can not be null")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
