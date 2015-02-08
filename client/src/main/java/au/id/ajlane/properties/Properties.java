package au.id.ajlane.properties;

import java.util.regex.Pattern;

public abstract class Properties
{
    private static final Pattern GETTER_PREFIX = Pattern.compile("^(get|is)(?=\\p{Lu})");

    public static String fieldName(final String getterName)
    {
        final String upperCamel = GETTER_PREFIX.matcher(getterName)
                                               .replaceAll("");
        return Character.toLowerCase(upperCamel.charAt(0)) + upperCamel.substring(1);
    }

    public static String getterName(final String fieldName)
    {
        return getterName(fieldName, false);
    }

    public static String getterName(final String fieldName, final boolean flagProperty)
    {
        final String upperCamel = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return (flagProperty ? "is" : "get") + upperCamel;
    }

    private Properties()
    {
    }
}
