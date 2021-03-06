package com.kray.messenger.resources;

import com.kray.messenger.beans.MessageFilterBean;
import com.kray.messenger.hateoas.MessageReferencesBuilder;
import com.kray.messenger.model.Link;
import com.kray.messenger.model.Message;
import com.kray.messenger.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private MessageService messageService = new MessageService();

    @GET
    public List<Message> getAllMessages(@BeanParam MessageFilterBean params,
                                        @Context UriInfo uriInfo) {
        List<Message> messages = messageService.getAllMessages();

        if (params.getStart() >= 0 && params.getSize() > 0) {
            messages = messageService.getMessagesPaginated(params.getStart(), params.getSize(), params.getYear());
        } else if (params.getYear() > 0) {
            messages = messageService.getMessagesByYear(params.getYear());
        }

        for (Message message : messages) {
            MessageReferencesBuilder builder = new MessageReferencesBuilder(uriInfo, message);
            message.getLinks().add(builder.getSelfLink());
            message.getLinks().add(builder.getProfileLink());
            message.getLinks().add(builder.getCommentsLink());
        }
        return messages;
    }

    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long messageId,
                              @Context UriInfo uriInfo) {
        Message message = messageService.getMessageById(messageId);
        MessageReferencesBuilder builder = new MessageReferencesBuilder(uriInfo, message);
        message.getLinks().add(builder.getSelfLink());
        message.getLinks().add(builder.getProfileLink());
        message.getLinks().add(builder.getCommentsLink());
        return message;
    }

    @POST
    public Response addMessage(Message message, @Context UriInfo uriInfo) {
        Message newMessage = messageService.addMessage(message);

        if (newMessage != null) {
            String newMessageId = String.valueOf(newMessage.getId());
            URI location = uriInfo.getAbsolutePathBuilder().path(newMessageId).build();
            return Response.created(location).entity(newMessage).build();
        } else {
            return Response.notModified().build();
        }
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
