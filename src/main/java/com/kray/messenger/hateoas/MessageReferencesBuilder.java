package com.kray.messenger.hateoas;

import com.kray.messenger.model.Link;
import com.kray.messenger.model.Message;
import com.kray.messenger.resources.CommentSubResource;
import com.kray.messenger.resources.MessageResource;
import com.kray.messenger.resources.ProfileResource;

import javax.ws.rs.core.UriInfo;

public class MessageReferencesBuilder {

    private UriInfo uriInfo;
    private Message message;

    public MessageReferencesBuilder(UriInfo uriInfo, Message message) {
        this.uriInfo = uriInfo;
        this.message = message;
    }

    public Link getSelfLink() {
        String uri = uriInfo.getBaseUriBuilder()
                    .path(MessageResource.class)
                    .path(String.valueOf(message.getId()))
                    .build().toString();
        return new Link(uri, "self");
    }

    public Link getProfileLink() {
        String uri = uriInfo
                    .getBaseUriBuilder()
                    .path(ProfileResource.class)
                    .path(message.getAuthor())
                    .build().toString();
        return new Link(uri, "profile");
    }

    public Link getCommentsLink() {
        String uri = uriInfo
                    .getBaseUriBuilder()
                    .path(MessageResource.class)
                    .path(MessageResource.class, "redirectToCommentsSubResource")
                    .resolveTemplate("messageId", message.getId())
                    .build().toString();
        return new Link(uri, "comments");
    }

}
