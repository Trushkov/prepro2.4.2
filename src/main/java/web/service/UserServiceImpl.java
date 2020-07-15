package web.service;

import web.dao.UserDaoImpl;
import web.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserDaoImpl dao;

    @Autowired
    public void setDao(UserDaoImpl dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    @Transactional
    public void remove(long id) {
        dao.remove(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return dao.getUsers();
    }
}
