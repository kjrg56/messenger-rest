package com.kray.messenger.resources;

import com.kray.messenger.model.Comment;
import com.kray.messenger.service.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentSubResource {

    private CommentService commentService = new CommentService();

    @GET
    public List<Comment> getCommentsByMessageId(@PathParam("messageId") long messageId) {
        return commentService.getAllComments(messageId);
    }

    @POST
    public Comment addComment(@PathParam("messageId") long messageId,
                                       Comment comment) {
        return commentService.createComment(comment, messageId);
    }

    @PUT
    @Path("/{commentId}")
    public Comment updateComment(@PathParam("commentId") long commentId,
                                 @PathParam("messageId") long messageId,
                                 Comment comment) {
        comment.setId(commentId);
        return commentService.updateComment(comment, messageId);
    }

    @DELETE
    @Path("/{commentId}")
    public Response deleteComment(@PathParam("commentId") long commentId) {
        if (!commentService.deleteComment(commentId)) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.ok().build();
    }

}
