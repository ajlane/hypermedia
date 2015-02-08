package au.id.ajlane.hypermedia.demo;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;

@Path("/")
public class BlogService
{
    @GET
    public Index list(
        @QueryParam("page") @DefaultValue("1") final int page, @Context final UriInfo uriInfo
    )
    {
        return new IndexData(
            uriInfo.getAbsolutePathBuilder()
                   .replaceQuery(null)
                   .build(),
            new PagingData<Index>(1, 1, null, null, null),
            Collections.emptyList()
        );
    }
}
