package com.demo.demo.Security;

import com.demo.demo.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //tells spring that it needs to do something with 'SecurityConfigurer' class
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override //override UserDetailService
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()// disables csrf
                .authorizeRequests().antMatchers("/authenticate", "/user/all", "/user/find/{emailAddress}", "/user/add",
                        "/user/update/{emailAddress}", "/user/delete/{emailAddress}").permitAll() //authorises requests
                .antMatchers("/admin").hasAnyAuthority("Administrator")
                .antMatchers("/data").hasAnyAuthority("Data Capture")
                //.antMatchers("/hr").hasAnyAuthority("Human Resource")
                .anyRequest().authenticated() //any other requests needs to be authenticated
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //don't create sessions
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    //add this to lets spring know that AuthenticationManager bean exists (this used to be a default bean in older versions of spring)
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //password is 'sam', this tells spring framework not to do any hashing
        //Hashing is the practice of using an algorithm to map data of any size to a fixed length
        return NoOpPasswordEncoder.getInstance();
    }
}
