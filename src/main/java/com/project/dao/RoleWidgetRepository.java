package com.project.dao;

import com.project.model.Role;
import com.project.model.RoleWidget;
import com.project.model.WidgetMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RoleWidgetRepository extends CrudRepository<RoleWidget, Integer> {
    List<RoleWidget> findAll();

    RoleWidget findByRoleAndWidgetMenu(Role role, WidgetMenu widgetMenu);

    @Query("select r.widgetMenu from RoleWidget r where r.role.id = :roleId and r.status = 1")
    List<WidgetMenu> findActiveByRole(Integer roleId);
}
