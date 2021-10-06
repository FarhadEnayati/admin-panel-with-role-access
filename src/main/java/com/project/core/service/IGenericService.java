package com.project.core.service;

import com.project.model.BaseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public interface IGenericService<T extends BaseEntity<PK>, PK extends Serializable> {

    List<T> getAll();

    T get(PK id);

    PK save(T entity);

    void update(T entity);

}
