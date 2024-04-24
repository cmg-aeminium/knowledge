/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.identity;

import javax.ejb.Stateless;
import pt.cmg.sweranker.dao.JPACrudDAO;
import pt.cmg.sweranker.persistence.entities.identity.User;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class UserDAO extends JPACrudDAO<User> {
    public UserDAO() {
        super(User.class);
    }
}
