package au.id.ajlane.hypermedia.demo;

import java.net.URI;
import java.util.Collections;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class BlogService {

  @GET
  public Index list(
      @QueryParam("page") @DefaultValue("1") final int page,
      @Context final UriInfo uriInfo
  ) {
    return new Index(
        uriInfo.getAbsolutePathBuilder().replaceQuery(null).build(),
        Collections.emptyList(),
        new Paging<>(
            1,
            1,
            null,
            null,
            uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build()
        )
    );
  }
}
