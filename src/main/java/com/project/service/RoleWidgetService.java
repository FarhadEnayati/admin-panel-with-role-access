package com.project.service;

import com.project.core.service.impl.GenericService;
import com.project.dao.RoleWidgetRepository;
import com.project.model.RoleWidget;
import com.project.utility.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleWidgetService extends GenericService<RoleWidget, Integer> {

    @Autowired
    private RoleWidgetRepository roleWidgetRepo;

    @Override
    public List<RoleWidget> getAll() {
        return roleWidgetRepo.findAll();
    }

    @Override
    public RoleWidget get(Integer id) {
        return roleWidgetRepo.findById(id).get();
    }

    @Override
    public Integer save(RoleWidget entity) {
        validate(entity);
        return roleWidgetRepo.save(entity).getId();
    }

    @Override
    public void update(RoleWidget obj) {
        validate(obj);
        roleWidgetRepo.save(obj);
    }

    public void validate(RoleWidget entity) {
        Assert.TRUE(!checkExist(entity), "unique.constraint");
    }

    private Boolean checkExist(RoleWidget entity) {
        RoleWidget res = roleWidgetRepo.findByRoleAndWidgetMenu(entity.getRole(), entity.getWidgetMenu());
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
