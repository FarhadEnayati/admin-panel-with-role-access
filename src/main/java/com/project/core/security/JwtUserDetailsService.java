package com.project.core.security;

import com.project.core.exception.ApplicationExceptions;
import com.project.core.exception.ApplicationExceptionsType;
import com.project.model.WidgetMenu;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Service
@Primary
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private Set authorities = new HashSet();

    private Map<Integer, WidgetMenu> map = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userService.userCacheEvict();
        com.project.model.User user = userService.findByLoginName(username);
        if (user != null) {
            if (user.getStatus().equals(Short.parseShort("0"))) {
                throw new ApplicationExceptions("auth.account.disabled");
            }
            Date today = getZeroTimeDate(new Date());
            if (getZeroTimeDate(user.getExpiredDate()).before(today)) {
                throw new ApplicationExceptions("auth.account.expired");
            }
            if (user.getPasswordExpiredDate().before(today)) {
                throw new ApplicationExceptions("auth.token.password-expired", ApplicationExceptionsType.LOCK);
            }
            User fUser = new User(user.getLoginName(), user.getPassword(), authorities);
            return fUser;
        } else {
            throw new ApplicationExceptions("auth.wrong");
        }
    }

    private Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    public void setAuthorities(List<WidgetMenu> menus) {
        authorities = new HashSet();
        authorities.add(new SimpleGrantedAuthority("AUTHENTICATED"));

        for (WidgetMenu menu : menus) {
            menu.clearChild();
            map.put(menu.getId().intValue(), menu);
        }

        if (menus != null) {
            for (WidgetMenu menu : menus) {
                if (isActiveLeaf(menu)) {
                    String role = "ROLE_" + menu.getWidgetPath().toUpperCase();
                    authorities.add(new SimpleGrantedAuthority(role));
                }
            }
        }
    }

    private boolean isActiveLeaf(WidgetMenu menu) {
        if (menu.getMenuType() != 2) { // is not leaf
            return false;
        }
        Integer parentId = menu.getParentId();
        while (parentId != null) {
            if (map.get(parentId) == null) {
                return false;
            }
            parentId = map.get(parentId).getParentId();
        }
        return true;
    }

}