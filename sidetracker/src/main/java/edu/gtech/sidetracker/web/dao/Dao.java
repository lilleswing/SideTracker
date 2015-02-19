package edu.gtech.sidetracker.web.dao;

import edu.gtech.sidetracker.web.model.Comment;

import java.util.List;

public interface Dao<T> {

	public List<? extends T> getAll();

	public T getById(String id);

	void add(T t);
}
