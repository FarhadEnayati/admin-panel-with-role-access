package com.project.utility;

import com.project.dao.UserRepository;
import com.project.model.FUser;
import com.project.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {

    private static UserRepository userDao;

    public SecurityUtil(UserRepository userDao) {
        SecurityUtil.userDao = userDao;
    }

    public static User getCurrentUser() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken userPassAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                        .getContext().getAuthentication();
                String username = userPassAuth.getName();
                return userDao.findByLoginName(username);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public User getUser(String username) {
        return userDao.findByLoginName(username);
    }

    public String getToken() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken userPassAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                    .getContext().getAuthentication();
            return "bearer " + ((FUser) userPassAuth.getPrincipal()).getToken();
        }
        return null;
    }

    public String[] getAuthorities() {
        return null;
    }

    public Boolean hasRole(String role) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken userPassAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                    .getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) userPassAuth.getPrincipal();
            for (GrantedAuthority auth : userDetails.getAuthorities()) {
                if (auth.getAuthority().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}
