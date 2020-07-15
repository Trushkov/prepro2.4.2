package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.UserDaoImpl;
import web.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserDaoImpl dao;

    @Autowired
    public void setDao(UserDaoImpl dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = dao.getUserByName(login);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: "+login);
        }
        return dao.getUserByName(login);
    }
}
