/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.schools.dto.response;

import pt.cmg.sweranker.api.rest.common.dto.response.CountryDTO;

/**
 * @author Carlos Gonçalves
 */
public class SchoolDTO {
    public long id;
    public String name;
    public CountryDTO country;
}
