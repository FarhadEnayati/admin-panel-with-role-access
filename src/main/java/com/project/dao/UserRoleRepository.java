package com.project.dao;

import com.project.model.Role;
import com.project.model.User;
import com.project.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    List<UserRole> findAll();

    UserRole findByUserAndRole(User user, Role role);

    @Query("select r.role from UserRole r where r.user = :user and r.status = 1")
    List<Role> findActiveByUser(User user);
}
