package edu.gtech.sidetracker.web.server;

import edu.gtech.sidetracker.web.dao.Dao;
import edu.gtech.sidetracker.web.model.Comment;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path( "/comment" )
public class CommentService {

	private Dao<Comment> commentDao;

	@Inject
	public CommentService(Dao<Comment> commentDao) {
		this.commentDao = commentDao;
	}

	@GET
	@Produces( APPLICATION_JSON )
	public List<? extends Comment> getAll() {
		return commentDao.getAll();
	}

	@GET
	@Path( "{id}" )
	@Produces( APPLICATION_JSON )
	public Comment getById( @PathParam( "id" ) String id ) {
		return commentDao.getById( id );
	}
}
