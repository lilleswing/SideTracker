package edu.gtech.sidetracker.web.dao;

import java.util.List;

import edu.gtech.sidetracker.web.model.Comment;

public interface Dao<T> {

	public List<? extends T> getAll();

	public T getById(long id);

	public T add(T t);
}
