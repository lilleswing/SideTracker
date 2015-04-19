package edu.gtech.sidetracker.web.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Singleton
public class CommentDao implements Dao<Comment> {

    private final SessionFactory sessionFactory;

    @Inject
    public CommentDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<? extends Comment> getAll() {
        return new ArrayList<>();
    }

    @Override
    public Comment getById(long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Comment) session.get(Comment.class, id);
    }

    @Override
    public void add(Comment comment) {
        int i = 1;
    }
}
