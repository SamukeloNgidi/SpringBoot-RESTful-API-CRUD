package com.demo.demo.Service;
import java.util.*;

import com.demo.demo.Model.Roles;
import com.demo.demo.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
    @Autowired
    private Users users;

    public MyUserDetails(Users users, Collection<GrantedAuthority> granted) {
        this.users = users;
        this.granted = granted;
    }

    private Collection<GrantedAuthority>granted;
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return granted;
    }

        @Override
        public String getPassword() {
            return users.getPassword();
        }

        @Override
        public String getUsername() {
            return users.getEmailAddress();
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
}
