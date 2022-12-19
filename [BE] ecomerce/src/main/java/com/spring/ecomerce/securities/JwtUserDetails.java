package com.spring.ecomerce.securities;

import com.spring.ecomerce.entities.clone.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {

    private final UserEntity userLogin;
    private final String fullname;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean confirmed;

    public JwtUserDetails(UserEntity userLogin, Collection<? extends GrantedAuthority> authorities) {
        this.userLogin = userLogin;
        this.fullname = userLogin.getFirstname() + " " + userLogin.getLastname();
        this.username = userLogin.getPhonenumber();
        this.password = userLogin.getPassword();
        this.authorities = authorities;
        this.confirmed = userLogin.isConfirmed();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getFullname() {
        return fullname;
    }

    public UserEntity getUserLogin(){ return userLogin;}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return confirmed;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return confirmed;
    }
}