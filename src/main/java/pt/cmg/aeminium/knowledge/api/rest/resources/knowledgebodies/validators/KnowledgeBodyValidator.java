/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.validators;

import java.util.List;
import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.users.dao.identity.UserDAO;
import pt.cmg.aeminium.datamodel.users.entities.identity.User;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.SearchKnowledgeBodyDTO;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@Dependent
public class KnowledgeBodyValidator {

    @Inject
    private UserDAO userDAO;

    public Optional<List<ErrorDTO>> isSearchValid(SearchKnowledgeBodyDTO filter) {

        if (filter.createdBy != null) {
            User createdBy = userDAO.findById(filter.createdBy);
            if (createdBy == null) {
                return Optional.of(List.of(new ErrorDTO(1, "User with id " + filter.createdBy + " does not exist")));
            }
        }

        return Optional.empty();
    }

}
