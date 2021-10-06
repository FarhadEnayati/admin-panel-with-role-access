package com.project.service;

import com.project.core.service.impl.GenericService;
import com.project.dao.RoleRepository;
import com.project.dao.UserRoleRepository;
import com.project.model.Role;
import com.project.model.User;
import com.project.utility.Assert;
import com.project.utility.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends GenericService<Role, Integer> {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Override
    public Integer save(Role entity) {
        validate(entity);
        roleRepo.save(entity);
        return entity.getId();
    }

    @Override
    public void update(Role entity) {
        validate(entity);
        roleRepo.save(entity);
    }

    @Override
    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role get(Integer id) {
        return roleRepo.findById(id).get();
    }

    public List<Role> listCurrentUserRoles() {
        return listUserRoles(SecurityUtil.getCurrentUser());
    }

    public List<Role> listUserRoles(User user) {
        return userRoleRepo.findActiveByUser(user);
    }

    public void validate(Role entity) {
        Assert.TRUE(!checkExist(entity), "unique.constraint");
    }

    private Boolean checkExist(Role entity) {
        Role res = roleRepo.findByRoleTitle(entity.getRoleTitle());
        if (res == null) {
            return false;
        }
        if (entity.getId() == null) {
            return true;
        } else if (entity.getId().equals(res.getId())) {
            return false;
        }
        return true;
    }

}
