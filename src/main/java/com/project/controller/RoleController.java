package com.project.controller;

import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.Role;
import com.project.service.RoleService;
import com.project.utility.dozer.RequestView;
import com.project.utility.dozer.ResponseView;
import com.project.view.RoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role/")
@PreAuthorize("hasRole('ROLE_MANAGEMENT/ROLE')")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ResponseView(RoleView.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Role> listRoles() {
        return roleService.getAll();
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequestView(RoleView.class)
    public Integer saveRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @RequestView(RoleView.class)
    public void editRole(@RequestBody Role role) {
        roleService.update(role);
        setException("edit.successful", ApplicationExceptionsType.SUCCESS);
    }

    @ResponseView(RoleView.class)
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Role getUser(@PathVariable Integer id) {
        return roleService.get(id);
    }
}
