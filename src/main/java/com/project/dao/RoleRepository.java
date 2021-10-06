package com.project.dao;

import com.project.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();

    Role findByRoleTitle(String roleTitle);
}
