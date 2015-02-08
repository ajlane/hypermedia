package au.id.ajlane.hypermedia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface Action
{
    String method() default "POST";
}
