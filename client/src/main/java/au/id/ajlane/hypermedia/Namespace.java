package au.id.ajlane.hypermedia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
public @interface Namespace {
  public String value();
}
