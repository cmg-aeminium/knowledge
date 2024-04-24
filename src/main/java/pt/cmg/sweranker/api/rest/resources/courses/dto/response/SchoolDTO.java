/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import pt.cmg.sweranker.api.rest.common.dto.response.CountryDTO;

/**
 * @author Carlos Gonçalves
 */
public class SchoolDTO {
    public Long id;
    public String name;
    public CountryDTO country;
    public LocalDateTime createdAt;

}
