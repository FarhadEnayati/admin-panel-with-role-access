package com.project.view;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleUserView extends BaseEntityView<Integer> {
    @NotNull
    private Integer userId;
    private String userName;
    private String loginName;
    @NotNull
    private Integer roleId;
    private String roleName;
    @NotNull
    private Short status;
}
