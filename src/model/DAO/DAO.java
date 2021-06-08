package model.DAO;

import java.util.List;

public interface DAO<T> {
public void insert();
public void update();
public void deleteById();
public T findById(T t);
public List<T> findAll();

}
