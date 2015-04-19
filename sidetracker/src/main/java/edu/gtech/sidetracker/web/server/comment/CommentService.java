package edu.gtech.sidetracker.web.server.comment;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Provider;
import edu.gtech.sidetracker.web.dao.CommentDao;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.Comment;
import edu.gtech.sidetracker.web.server.comment.model.WsComment;

@Path( "/comment" )
public class CommentService {

	private Provider<RequestState> requestStateProvider;
	private CommentDao commentDao;

	@Inject
	public CommentService(Provider<RequestState> requestStateProvider,
						  CommentDao commentDao) {
		this.requestStateProvider = requestStateProvider;
		this.commentDao = commentDao;
	}

	@GET
	@Produces( APPLICATION_JSON )
	public List<WsComment> getAll() {
		final List<? extends Comment> comments = commentDao.getAll();
		final List<WsComment> retvals = new ArrayList<>(comments.size());
		for (final Comment comment: comments) {
			retvals.add(new WsComment(comment));
		}
		return retvals;
	}

	@POST
	@Produces(APPLICATION_JSON)
	public WsComment create(final WsComment wsComment) {
		final Comment comment = new Comment();
		comment.setComment(wsComment.getComment());
		final Comment persisted = commentDao.add(comment);
		return new WsComment(persisted);
	}

	@GET
	@Path( "{id}" )
	@Produces( APPLICATION_JSON )
	public WsComment getById( @PathParam( "id" ) long id ) {
		return new WsComment(commentDao.getById( id ));
	}
}
