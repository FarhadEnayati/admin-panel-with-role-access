package com.project.core.service.impl;

import com.project.core.service.IGenericService;
import com.project.model.BaseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class GenericService<T extends BaseEntity<PK>, PK extends Serializable>
        implements IGenericService<T, PK> {

    @Override
    public List<T> getAll() {
        return getAll();
    }

    @Override
    public T get(PK id) {
        return get(id);
    }

    @Override
    public PK save(T entity) {
        return save(entity);
    }

    @Override
    public void update(T entity) {
        update(entity);
    }
}
