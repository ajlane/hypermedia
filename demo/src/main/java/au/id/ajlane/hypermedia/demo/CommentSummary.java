package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;

@Hypermedia
public interface CommentSummary
{
    int getCount();

    PostSummary getPost();
}
