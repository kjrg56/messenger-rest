package com.kray.messenger.resources;

import com.kray.messenger.beans.MessageFilterBean;
import com.kray.messenger.model.Comment;
import com.kray.messenger.model.Message;
import com.kray.messenger.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private MessageService messageService = new MessageService();

    @GET
    public List<Message> getAllMessages(@BeanParam MessageFilterBean params) {
        if (params.getStart() >= 0 && params.getSize() > 0) {
            return messageService.getMessagesPaginated(params.getStart(), params.getSize(), params.getYear());
        } else if (params.getYear() > 0) {
            return messageService.getMessagesByYear(params.getYear());
        }
        return messageService.getAllMessages();
    }

    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long messageId) {
        return messageService.getMessageById(messageId);
    }

    @POST
    public Message addMessage(Message message) {
        return messageService.addMessage(message);
    }

    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
        message.setId(messageId);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public Response deleteMessage(@PathParam("messageId") long messageId) {
        if (!messageService.deleteMessage(messageId)) {
            return Response.notModified().build();
        }
        return Response.ok().build();
    }

    @Path("/{messageId}/comments")
    public CommentSubResource redirectToCommentsSubResource() {
        // All HTTP methods that match the path will be redirected
        return new CommentSubResource();
    }

}