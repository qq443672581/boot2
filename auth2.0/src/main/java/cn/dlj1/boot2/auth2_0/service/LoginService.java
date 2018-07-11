package cn.dlj1.boot2.auth2_0.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class LoginService implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Collection<GrantedAuthority> collections = new ArrayList<>();
        GrantedAuthority g = new SimpleGrantedAuthority("admin");
        collections.add(g);

        UserDetails userDetails = new User(
                "test",
                "test",
                collections
        );
        return userDetails;
    }
}
