package com.project.view;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenuRoleView extends BaseEntityView<Integer> {
    @NotNull
    private Integer menuId;
    private String menuName;
    @NotNull
    private Integer roleId;
    private String roleName;
    @NotNull
    private Short status;
}
