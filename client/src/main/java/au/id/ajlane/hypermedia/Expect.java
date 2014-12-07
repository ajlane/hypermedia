package au.id.ajlane.hypermedia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Expect {

  public static final String BINARY = "java.io.InputStream";
  public static final String NOTHING = "void";

  public int status() default 200;

  public String value() default NOTHING;
}
