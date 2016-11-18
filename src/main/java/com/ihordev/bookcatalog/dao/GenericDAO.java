package com.ihordev.bookcatalog.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<E, ID extends Serializable>
{
    void add(E entity);

    void update(E entity);

    void delete(E entity);

    E findById(ID id);

    E findReferenceById(ID id);

    List<E> findAll();
}
