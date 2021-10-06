package com.project.controller;

import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.RoleWidget;
import com.project.service.RoleWidgetService;
import com.project.utility.dozer.RequestView;
import com.project.utility.dozer.ResponseView;
import com.project.view.MenuRoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/menu-role/")
@PreAuthorize("hasRole('ROLE_MANAGEMENT/MENU-ROLE')")
public class MenuRoleController extends BaseController {

    @Autowired
    private RoleWidgetService roleWidgetService;

    @ResponseView(MenuRoleView.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<RoleWidget> listRoleWidgets() {
        return roleWidgetService.getAll();
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequestView(MenuRoleView.class)
    public Integer saveRoleWidget(@RequestBody RoleWidget role) {
        return roleWidgetService.save(role);
    }

    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @RequestView(MenuRoleView.class)
    public void editRoleWidget(@RequestBody RoleWidget role) {
        roleWidgetService.update(role);
        setException("edit.successful", ApplicationExceptionsType.SUCCESS);
    }

    @ResponseView(MenuRoleView.class)
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public RoleWidget getRoleWidget(@PathVariable Integer id) {
        return roleWidgetService.get(id);
    }
}
