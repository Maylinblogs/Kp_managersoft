package org.menegment.interfeses;

import java.util.List;

public interface DAO<T> {

    T findEntity(Integer id);

    T findEntity(String id);

    boolean saveEntity(T entity);

    boolean updateEntity(T entity);

    boolean deleteEntityId(Integer id);
    boolean deleteEntityId(String id);

    List<T> findAllEntity();

}
