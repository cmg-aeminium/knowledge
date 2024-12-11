/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.common.dto.response;

/**
 * @author Carlos Gonçalves
 */
public record CountryDTO(
    long id,
    String alpha2code,
    String name) {
}
