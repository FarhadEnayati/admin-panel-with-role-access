package com.project.controller;

import com.project.core.exception.ApplicationExceptions;
import com.project.core.security.JwtTokenUtil;
import com.project.core.security.JwtUserDetailsService;
import com.project.model.Role;
import com.project.model.User;
import com.project.model.security.JwtChangePassRequest;
import com.project.model.security.JwtRequest;
import com.project.model.security.JwtResponse;
import com.project.service.RoleService;
import com.project.service.UserService;
import com.project.service.WidgetMenuService;
import com.project.utility.Assert;
import com.project.utility.SecurityUtil;
import com.project.utility.dozer.ResponseView;
import com.project.view.JwtResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("jwt")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private WidgetMenuService widgetMenuService;

    @ResponseView(JwtResponseView.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        Objects.requireNonNull(authenticationRequest.getUsername());
        Objects.requireNonNull(authenticationRequest.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            // throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new ApplicationExceptions("auth.wrong");
        }

        User currentUser = securityUtil.getUser(authenticationRequest.getUsername());
        List<Role> roles = roleService.listUserRoles(currentUser).stream().
                filter(e -> Short.parseShort("1") == e.getStatus()).collect(Collectors.toList());
        Integer selectedRole = null;
        if (roles != null && roles.size() != 0) {
            if (!Assert.isNull(authenticationRequest.getRole())) {
                if (roles.stream().noneMatch(r -> r.getId().equals(authenticationRequest.getRole()))) {
                    throw new ApplicationExceptions();
                }
                selectedRole = authenticationRequest.getRole();
            } else if (roles.size() == 1) {
                selectedRole = roles.get(0).getId();
            }
        }

        if (roles == null || roles.size() == 0) {
            throw new ApplicationExceptions("auth.role.not-found");
        }

        String token = null;
        Long expiration = null;
        if (selectedRole != null) {
            userDetailsService.setAuthorities(widgetMenuService.listMenu(selectedRole));
            currentUser.setLastLoginDate(new Date());
            userService.updateLastLoginDate(currentUser);
            expiration = JwtTokenUtil.getExpirationTime();
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());
            token = jwtTokenUtil.generateToken(userDetails);
            roles = null;
        }
        return new JwtResponse(token, selectedRole, roles, expiration);
    }

    @ResponseView(JwtResponseView.class)
    @RequestMapping(value = "/change-pass", method = RequestMethod.POST)
    public JwtResponse changePassword(@RequestBody JwtChangePassRequest authenticationRequest) {
        userService.changePassword(authenticationRequest);
        return createAuthenticationToken(new JwtRequest(authenticationRequest.getUsername(), authenticationRequest.getPasswordNew(), null));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean logout() {
        return userService.userCacheEvict();
    }

}
