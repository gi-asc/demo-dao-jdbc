package model.DAO;

import java.util.List;

public interface DAO<T> {
public void insert(T t);
public void update(T t);
public void deleteById(Integer id);
public T findById(Integer i);
public List<T> findAll();

}
