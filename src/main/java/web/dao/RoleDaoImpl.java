package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.save(role);
    }

    @Override
    public Role getRole(String name) {
        Role role = null;
        try{
            role = (Role) sessionFactory.getCurrentSession().createQuery("from Role where name = :name")
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException nre){

        }
        return role;
    }

    @Override
    public boolean hasRole(String name) {
        return (getRole(name)!= null);
    }
}
