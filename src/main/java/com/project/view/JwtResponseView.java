package com.project.view;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseView extends BaseEntityView {

    private String token;
    private Integer selectedRole;
    private List<RoleView> roles;
    private Long expiration;

}