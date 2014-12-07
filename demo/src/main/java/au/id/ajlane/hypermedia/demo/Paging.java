package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.Expect;
import au.id.ajlane.hypermedia.Link;
import au.id.ajlane.hypermedia.LinkParams;

import java.net.URI;

public class Paging<T> {

  private final int maxPages;
  private final URI next;
  private final int page;
  private final URI previous;
  private final URI specificPage;

  public Paging(
      final int page,
      final int maxPages,
      final URI next,
      final URI previous,
      final URI specificPage
  ) {
    this.page = page;
    this.maxPages = maxPages;
    this.next = next;
    this.previous = previous;
    this.specificPage = specificPage;
  }

  public int getMaxPages() {
    return maxPages;
  }

  @Link
  @Expect("T")
  public URI getNext() {
    return next;
  }

  public int getPage() {
    return page;
  }

  @Link
  @Expect("T")
  public URI getPrevious() {
    return previous;
  }

  @Link
  @LinkParams("page")
  @Expect("T")
  public URI getSpecificPage() {
    return specificPage;
  }
}
