/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.users.dto.request;

/**
 * @author Carlos Gonçalves
 */
public class PasswordChangeDTO {
    public String oldPassword;
    public String newPassword;
}
