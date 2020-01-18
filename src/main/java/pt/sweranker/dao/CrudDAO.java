/*
 * Copyright (c) 2019  Fashion Concierge
 * All rights reserved.
 */
package pt.sweranker.dao;

import java.util.List;

/**
 * @author Carlos Manuel
 */
public interface CrudDAO<T> {

    /**
     * Persists an entity into the system.
     *
     * @param entity
     */
    void create(T entity);

    /**
     * Persists an entity into the system with the possibility to force or not the changes into the
     * persistence context
     *
     * @param entity
     * @param flush true if forcing is needed, false otherwise
     */
    void create(T entity, boolean flush);

    /**
     * Edits an Entity
     *
     * @param entity
     */
    void edit(T entity);

    /**
     * Removes an entity from the database
     *
     * @param entity
     */
    void remove(T entity);

    /**
     * Retrieves and entity by its numeric ID.
     *
     * @param id
     * @return the found entity instance or null if the entity does not exist
     */
    T findById(Long id);

    /**
     * Retrieves the full list of Entities of its type with no restrictions.
     *
     * @return a list of the results (empty if none was found)
     */
    List<T> findAll();

    /**
     * Retrieves the total number of Entities of its type with no restrictions.
     *
     * @return a list of the results (empty if none was found)
     */
    Long count();

}
