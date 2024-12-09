/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import pt.cmg.aeminium.knowledge.api.rest.common.dto.response.CountryDTO;

/**
 * @author Carlos Gonçalves
 */
public record SchoolDTO(
    Long id,
    String name,
    CountryDTO country,
    LocalDateTime createdAt) {}
