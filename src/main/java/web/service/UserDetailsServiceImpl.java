package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDaoImpl;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.Set;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    UserDaoImpl dao;

    @Autowired
    public void setDao(UserDaoImpl dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = dao.getUserByName(login);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: "+login);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername()
                , user.getPassword()
                , grantedAuthorities);
    }
        //return user;
    //}
}
