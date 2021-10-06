package com.project.controller;

import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.User;
import com.project.model.rest.UserInfo;
import com.project.service.UserService;
import com.project.utility.dozer.RequestView;
import com.project.utility.dozer.ResponseView;
import com.project.view.UserListView;
import com.project.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
@PreAuthorize("hasRole('ROLE_MANAGEMENT/USER')")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseView(UserListView.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<User> listUsers() {
        return userService.getAll();
    }

    @RequestView(UserView.class)
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public UserInfo saveUser(@RequestBody User user) {
        String password = userService.saveAndGetPassword(user);
        UserInfo info = new UserInfo();
        info.setId(user.getId());
        info.setPassword(password);
        return info;
    }

    @RequestView(UserView.class)
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public void editUser(@RequestBody User user) {
        userService.update(user);
        setException("edit.successful", ApplicationExceptionsType.SUCCESS);
    }

    @RequestMapping(value = "resetPassword/{id}", method = RequestMethod.GET)
    public UserInfo resetPassword(@PathVariable Integer id) {
        String password = userService.resetPassword(id);
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setPassword(password);
        return info;
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseView(UserView.class)
    @RequestView(UserView.class)
    public User getUser(@PathVariable Integer id) {
        return userService.get(id);
    }

}
