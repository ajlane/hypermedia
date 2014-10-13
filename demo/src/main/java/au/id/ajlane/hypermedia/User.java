package au.id.ajlane.hypermedia;

import java.util.Set;

@Hypermedia
public class User {
    private String name;

    private Set<String> friends;

    @Link(User.class)
    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(final Set<String> friends) {
        this.friends = friends;
    }
}
