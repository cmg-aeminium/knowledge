/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import pt.cmg.jakartaexample.microtwo.api.v1.resources.users.dto.request.NewUserDTO;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
public class CourseValidator {

    public static Optional<List<ErrorDTO>> isValidUserForCreation(NewUserDTO userDTO) {
        List<ErrorDTO> errors = new ArrayList<>();

        if (StringUtils.isBlank(userDTO.name)) {
            errors.add(new ErrorDTO(1));
        }

        if (StringUtils.isBlank(userDTO.email)) {
            errors.add(new ErrorDTO(2));
        }

        if (StringUtils.isBlank(userDTO.picture)) {
            errors.add(new ErrorDTO(2));
        }

        // now validate groups

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

}
