package com.kray.messenger.resources;

import com.kray.messenger.model.Profile;
import com.kray.messenger.service.ProfileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private ProfileService profileService = new ProfileService();

    @GET
    @Path("/{username}")
    public Profile getProfile(@PathParam("username") String username) {
        return profileService.getProfileByUsername(username);
    }

    @GET
    public List<Profile> getProfiles(@QueryParam("year") int year,
                                     @QueryParam("start") int start,
                                     @QueryParam("size") int size) {

        if (start >= 0 && size > 0) {
            return profileService.getProfilesPaginated(start, size, year);
        } else if (year > 0) {
            return profileService.getProfilesByYear(year);
        }
        return profileService.getAllProfiles();
    }

    @POST
    public Profile addProfile(Profile profile) {
        return profileService.addProfile(profile);
    }

    @PUT
    @Path("/{username}")
    public Profile updateProfile(@PathParam("username") String username,
                                 Profile profile) {
        profile.setUsername(username);
        return profileService.updateProfile(profile);
    }

    @DELETE
    @Path("/{username}")
    public Response deleteProfile(@PathParam("username") String username) {
        if (!profileService.deleteProfile(username)) {
            return Response.notModified().build();
        }
        return Response.ok().build();
    }

}
