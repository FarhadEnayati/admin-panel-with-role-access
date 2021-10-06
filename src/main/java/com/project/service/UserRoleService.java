package com.project.service;

import com.project.core.service.impl.GenericService;
import com.project.dao.UserRoleRepository;
import com.project.model.UserRole;
import com.project.utility.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService extends GenericService<UserRole, Integer> {

    @Autowired
    private UserRoleRepository userRoleDao;

    @Override
    public List<UserRole> getAll() {
        return userRoleDao.findAll();
    }

    @Override
    public UserRole get(Integer id) {
        return userRoleDao.findById(id).get();
    }

    @Override
    public Integer save(UserRole obj) {
        validate(obj);
        return userRoleDao.save(obj).getId();
    }

    @Override
    public void update(UserRole obj) {
        validate(obj);
        userRoleDao.save(obj);
    }

    public void validate(UserRole entity) {
        Assert.TRUE(!checkExist(entity), "unique.constraint");
    }

    private Boolean checkExist(UserRole entity) {
        UserRole res = userRoleDao.findByUserAndRole(entity.getUser(), entity.getRole());
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
