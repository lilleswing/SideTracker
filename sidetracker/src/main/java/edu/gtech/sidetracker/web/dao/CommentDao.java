package edu.gtech.sidetracker.web.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.Comment;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CommentDao implements Dao<Comment> {

    // TODO (LESWING) hook up to hibernate and sqlite
    private List<Comment> comments;

    @Inject
    public CommentDao() {
        this.comments = new ArrayList<>();
        final Comment comment = new Comment();
        comment.setId(1);
        comment.setUserName("Karl");
        comment.setComment("Example Service");
        this.comments.add(comment);
    }

    @Override
    public List<? extends Comment> getAll() {
        return comments;
    }

    @Override
    public Comment getById(String id) {
        final int idInt = Integer.valueOf(id);
        return comments.get(idInt);
    }
}
