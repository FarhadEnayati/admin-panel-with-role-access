package com.project.dao;

import com.project.model.WidgetMenu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface WidgetRepository extends CrudRepository<WidgetMenu, Integer> {
    List<WidgetMenu> findAll();

    @Modifying
    @Query("update WidgetMenu e set e.parentMenuNameFa=:name where e.parentId=:id")
    void updateParentNames(String name, Integer id);
}
