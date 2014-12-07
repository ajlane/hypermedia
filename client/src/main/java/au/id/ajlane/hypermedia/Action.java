package au.id.ajlane.hypermedia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Action {
  public String value();
  public String method() default "POST";
  public String accept() default "void";
  public Expect[] expect() default {};
}
