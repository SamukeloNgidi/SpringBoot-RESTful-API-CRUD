package com.demo.demo.Service;

import com.demo.demo.Model.Users;
import com.demo.demo.Repository.RolesRepository;
import com.demo.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        //spring calls this method to load user by username, the user could be in the database or somewhere else
        //return new User("sam","sam", new ArrayList<>()); //load User class in spring security. Create your own User class if you have specific things that you need to track

        Users users = userRepository.getUserByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("Could not find user");
        } else {
            List<GrantedAuthority> roles = new ArrayList<>();
            Long userRole = users.getRoleId();
            String roleName = rolesRepository.getRoleName(userRole);

            roles.add(new SimpleGrantedAuthority("ROLE_" + roleName));
            return new MyUserDetails(users, roles);
        }
    }
}
