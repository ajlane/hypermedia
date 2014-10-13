package au.id.ajlane.hypermedia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Link {
    public Method method() default Method.GET;
    public Class<?> accept() default Void.class;
    public int status() default 200;
    public Class<?> value() default Void.class;
}
