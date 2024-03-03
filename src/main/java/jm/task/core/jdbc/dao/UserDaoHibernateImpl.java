package jm.task.core.jdbc.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlString;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final UserDaoHibernateImpl INSTANCE =
            new UserDaoHibernateImpl();
    private static final EntityManagerFactory entityManagerFactory =
            Util.getEntityManagerFactory();

    public UserDaoHibernateImpl() {
    }

    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createUsersTable() {
        try (EntityManager entityManager = entityManagerFactory
                .createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager
                    .createNativeQuery(SqlString.CREATE_USERS_TABLE_PostgreSQL)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void dropUsersTable() {
        try (EntityManager entityManager = entityManagerFactory
                .createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(SqlString.DROP_USERS_TABLE_SQL)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (EntityManager entityManager = entityManagerFactory
                .createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(new User(name, lastName, age));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (EntityManager entityManager = entityManagerFactory
                .createEntityManager()) {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (EntityManager entityManager = entityManagerFactory
                .createEntityManager()) {
            return entityManager
                    .createQuery(SqlString.GET_ALL_USERS_HQL,User.class)
                    .getResultList();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (EntityManager entityManager = entityManagerFactory
                .createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager
                    .createNativeQuery(SqlString.CLEAN_USERS_TABLE_SQL)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }
}
