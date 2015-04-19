package edu.gtech.sidetracker.web.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.Comment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Singleton
public class CommentDao implements Dao<Comment> {

    private final Provider<RequestState> requestStateProvider;
    private final SessionFactory sessionFactory;

    @Inject
    public CommentDao(final Provider<RequestState> requestStateProvider,
                      final SessionFactory sessionFactory) {
        this.requestStateProvider = requestStateProvider;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<? extends Comment> getAll() {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(Comment.class);
        return criteria.list();
    }

    @Override
    public Comment getById(long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Comment) session.get(Comment.class, id);
    }

    @Override
    public Comment add(Comment comment) {
        comment.setAppUser(requestStateProvider.get().getAppUser());
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        final Serializable primaryKey = session.save(comment);
        transaction.commit();
        session.close();
        return getById(((Number)primaryKey).longValue());
    }
}
