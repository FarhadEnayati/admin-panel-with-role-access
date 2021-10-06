package com.project.service;

import com.project.core.exception.ApplicationExceptions;
import com.project.core.service.impl.GenericService;
import com.project.dao.RoleWidgetRepository;
import com.project.dao.UserRoleRepository;
import com.project.dao.WidgetRepository;
import com.project.model.Role;
import com.project.model.User;
import com.project.model.WidgetMenu;
import com.project.utility.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WidgetMenuService extends GenericService<WidgetMenu, Integer> {

    @Autowired
    private WidgetRepository widgetRepo;

    @Autowired
    private RoleWidgetRepository roleWidgetRepo;

    @Autowired
    private UserRoleRepository userRoleRepository;


    public List<WidgetMenu> listUserMenu(Integer roleId) {
        User currentUser = SecurityUtil.getCurrentUser();
        List<Role> roles = userRoleRepository.findActiveByUser(currentUser);
        if (roles.stream().noneMatch(r -> r.getId().equals(roleId))) {
            throw new ApplicationExceptions();
        }
        return listMenu(roleId);
    }

    public List<WidgetMenu> listMenu(Integer roleId) {
        if (roleId == null) {
            return null;
        }
        return roleWidgetRepo.findActiveByRole(roleId).stream().sorted(Comparator.comparingInt(WidgetMenu::getMenuOrder)).collect(Collectors.toList());
    }

    public List<WidgetMenu> createTree(List<WidgetMenu> menus) {
        Map<Integer, WidgetMenu> map = new HashMap<>();

        for (WidgetMenu menu : menus) {
            menu.clearChild();
            map.put(menu.getId().intValue(), menu);
        }

        for (WidgetMenu menu : menus) {
            Integer parentId = menu.getParentId();
            if (parentId == null || parentId == 0) {
                menu.setParentId(null);
            } else {
                WidgetMenu parent = map.get(parentId);
                if (parent != null) {
                    parent.addChild(menu);
                }
            }

        }
        List<WidgetMenu> res = menus.stream().filter(e -> e.getParentId() == null).collect(Collectors.toList());
        rebaseId(res, 0);
        return res;
    }

    private Integer rebaseId(List<WidgetMenu> menus, Integer fromId) {
        Integer menuId = fromId;
        for (int i = 0; i < menus.size(); i++) {
            if (!fromId.equals(0L)) {
                menus.get(i).setParentId(fromId.intValue());
            }
            menus.get(i).setId(++menuId);
            if (!menus.get(i).getChildren().isEmpty()) {
                menuId = rebaseId(menus.get(i).getChildren(), menuId);
            }
        }
        return menuId;
    }

    @Override
    public Integer save(WidgetMenu entity) {
        widgetRepo.save(entity);
        return entity.getId();
    }

    @Override
    public void update(WidgetMenu entity) {
        Short active = Short.parseShort("1");
        if (entity.getStatus().equals(active)) {
            if (entity.getParentId() != null) {
                WidgetMenu parent = this.get(entity.getParentId());
                if (!parent.getStatus().equals(active)) {
                    throw new ApplicationExceptions("fuel.bo.widget.menu.inactiv.parent");
                }
            }

        }
        widgetRepo.save(entity);
        widgetRepo.updateParentNames(entity.getMenuNameFa(), entity.getId());
    }

    @Override
    public List<WidgetMenu> getAll() {
        return widgetRepo.findAll();
    }

    @Override
    public WidgetMenu get(Integer id) {
        return widgetRepo.findById(id).get();
    }
}
