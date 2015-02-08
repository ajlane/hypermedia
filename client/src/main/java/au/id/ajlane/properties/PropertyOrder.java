package au.id.ajlane.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the preferred order for properties on a bean class.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertyOrder
{
    /**
     * A list of property names, in the preferred order.
     * <p>
     * Names should be specified in lower camel case, without any {@code get*} or {@code is*} prefixes. e.g. A property
     * with a getter named {@code getFruitBasket()} should be listed as {@code "fruitBasket"}.
     * </p>
     *
     * @return The list of property names, or an empty list.
     */
    public String[] value() default {};
}
