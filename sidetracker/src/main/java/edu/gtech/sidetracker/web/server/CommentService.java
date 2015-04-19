package edu.gtech.sidetracker.web.server;

import edu.gtech.sidetracker.web.dao.CommentDao;
import edu.gtech.sidetracker.web.dao.Dao;
import edu.gtech.sidetracker.web.model.Comment;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path( "/comment" )
public class CommentService {

	private CommentDao commentDao;

	@Inject
	public CommentService(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@GET
	@Produces( APPLICATION_JSON )
	public List<? extends Comment> getAll(@Context final Request request) {
		return commentDao.getAll();
	}

	@POST
	@Produces(APPLICATION_JSON)
	public Comment create(final Comment comment) {
		commentDao.add(comment);
		return comment;
	}

	@GET
	@Path( "{id}" )
	@Produces( APPLICATION_JSON )
	public Comment getById( @PathParam( "id" ) long id ) {
		return commentDao.getById( id );
	}
}
