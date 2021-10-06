package com.project.model.security;

import com.project.model.BaseEntity;
import com.project.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse extends BaseEntity {

    private String token;
    private Integer selectedRole;
    private List<Role> roles;
    private Long expiration;

    public JwtResponse(String token, Integer selectedRole, List<Role> roles, Long expiration) {
        this.token = token;
        this.selectedRole = selectedRole;
        this.roles = roles;
        this.expiration = expiration;
    }

}