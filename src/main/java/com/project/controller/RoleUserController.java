package com.project.controller;

import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.UserRole;
import com.project.service.UserRoleService;
import com.project.utility.dozer.RequestView;
import com.project.utility.dozer.ResponseView;
import com.project.view.RoleUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role-user/")
@PreAuthorize("hasRole('ROLE_MANAGEMENT/ROLE-USER')")
public class RoleUserController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @ResponseView(RoleUserView.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<UserRole> listUserRoles() {
        return userRoleService.getAll();
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequestView(RoleUserView.class)
    public Integer saveUserRole(@RequestBody UserRole role) {
        return userRoleService.save(role);
    }

    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @RequestView(RoleUserView.class)
    public void editUserRole(@RequestBody UserRole role) {
        userRoleService.update(role);
        setException("edit.successful", ApplicationExceptionsType.SUCCESS);
    }

    @ResponseView(RoleUserView.class)
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public UserRole getUserRole(@PathVariable Integer id) {
        return userRoleService.get(id);
    }
}
