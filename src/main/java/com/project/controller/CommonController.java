package com.project.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.Role;
import com.project.model.User;
import com.project.model.WidgetMenu;
import com.project.model.security.JwtChangePassRequest;
import com.project.service.RoleService;
import com.project.service.UserService;
import com.project.service.WidgetMenuService;
import com.project.utility.SecurityUtil;
import com.project.utility.converter.CustomDateSerializer;
import com.project.utility.dozer.ResponseView;
import com.project.view.LoggingUserView;
import com.project.view.WidgetMenuView1;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/common/")
public class CommonController extends BaseController {

    @Autowired
    private RoleService roleSrv;

    @Autowired
    private WidgetMenuService resourceSrv;

    @Autowired
    private UserService userService;

    @ResponseView(LoggingUserView.class)
    @GetMapping(value = "currentuser/{roleId}")
    public User getCurrentUser(@PathVariable Integer roleId) {
        User user = SecurityUtil.getCurrentUser();
        Optional<Role> role = roleSrv.listCurrentUserRoles().stream().filter(e -> roleId.equals(e.getId())).findFirst();
        if (role.isPresent()) {
            user.setEditDesc(role.get().getRoleTitle());
        }
        return user;
    }

    @RequestMapping(value = "user/change-pass", method = RequestMethod.POST)
    public void changeUserPassword(@RequestBody JwtChangePassRequest authenticationRequest) {
        authenticationRequest.setUsername(SecurityUtil.getCurrentUser().getLoginName());
        userService.changePassword(authenticationRequest);
        super.setException("auth.change-pass.success", ApplicationExceptionsType.SUCCESS);
    }

    @ResponseView(WidgetMenuView1.class)
    @GetMapping(value = "menu/role/{role}")
    public List<WidgetMenu> getAllMenu(@PathVariable Integer role) {
        List<WidgetMenu> menus = resourceSrv.listUserMenu(role);
        return resourceSrv.createTree(menus);
    }

    @GetMapping(value = "date")
    public CustomDate getDate() {
        CustomDate c = new CustomDate();
        c.setDate(new Date());
        return c;
    }

    @Data
    class CustomDate {
        @JsonSerialize(using = CustomDateSerializer.class)
        private Date date;
    }

}
