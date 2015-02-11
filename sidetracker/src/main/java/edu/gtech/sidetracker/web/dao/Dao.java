package edu.gtech.sidetracker.web.dao;

import java.util.List;

public interface Dao<T> {

	public List<? extends T> getAll();

	public T getById(String id);
}
