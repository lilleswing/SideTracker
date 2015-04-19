package edu.gtech.sidetracker.web.server.comment.model;

import edu.gtech.sidetracker.web.model.Comment;

public class WsComment {
    private long id;
    private String userName;
    private String comment;

    public WsComment() {
    }

    public WsComment(final Comment comment) {
        this.id = comment.getId();
        this.userName = comment.getAppUser().getUsername();
        this.comment = comment.getComment();
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }
}
