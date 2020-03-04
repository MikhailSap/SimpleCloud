package sap.gb.cloud.dao;

import org.hibernate.query.Query;
import sap.gb.cloud.entity.User;
import sap.gb.cloud.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDao  {

    public User findByLogin(String login) {
        Query query = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from User where login= :param");

        query.setParameter("param", login);
        List<User> users = query.list();
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
}
