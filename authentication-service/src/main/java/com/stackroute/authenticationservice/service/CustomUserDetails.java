package com.stackroute.authenticationservice.service;

import com.stackroute.authenticationservice.entity.Role;
import com.stackroute.authenticationservice.entity.User;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Generated
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user)
    {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role="";
        if(Role.owner.equals(user.getRole()))
        {
            role="owner";
        }
        else
        {
            role="user";
        }
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(role);
        List<GrantedAuthority>authorities=new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        log.info("Inside the method of getAuthorities() of CustomUserDetails class");
        log.info("Current User Authority"+authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        log.info("Inside getPassword() method of CustomUserDetails class");
        log.info("Current user password:"+ user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("Inside getUserName() method of CustomUserDetails class");
        log.info("Current user email:"+user.getEmail());
        return user.getEmail();
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
