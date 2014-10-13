package au.id.ajlane.hypermedia;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    @GET
    @Path("{id}")
    public User get(@PathParam("id") final String id) {
        return users.get(id);
    }

    @PUT
    @Path("{id}")
    public void put(@PathParam("id") final String id, final User user) {
        this.users.put(id, user);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") final String id) {
        this.users.remove(id);
    }
}
