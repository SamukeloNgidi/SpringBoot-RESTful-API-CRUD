package com.demo.demo.Controller;

import com.demo.demo.Model.AuthenticationRequest;
import com.demo.demo.Model.AuthenticationResponse;
import com.demo.demo.Repository.UserRepository;
import com.demo.demo.Service.JwtUtil;
import com.demo.demo.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService myUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired //Bind repository with controller
    UserRepository userRepository;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        //gets the username and password from the authentication request

        boolean correctPassword = userRepository.passwordIsCorrect(authenticationRequest.getPassword(), authenticationRequest.getUsername());

        if (correctPassword) {
            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails); //generate user token
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        else{
            // throw new Exception("Incorrect username or password");
            return ResponseEntity.ok("Incorrect username or password");
        }

        //if you hardcode username & password, use code below
        /*try {
            authenticationManager.authenticate( //authenticate with authenticationManager
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){ //if authentication fails, throw an exception
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername()); //get user details from the service

        final  String jwt = jwtTokenUtil.generateToken(userDetails); //get the token

        return ResponseEntity.ok(new AuthenticationResponse(jwt)); //return the jwt (AuthenticationResponse class)*/
    }
}
