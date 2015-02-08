package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Hypermedia;
import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.properties.PropertyOrder;

@Hypermedia
@PropertyOrder({"page", "maxPages", "previous", "next", "specificPage"})
public interface Paging<T>
{
    int getMaxPages();

    @Link
    T getNext();

    int getPage();

    @Link
    T getPrevious();

    @Link
    T getSpecificPage(int page);
}
