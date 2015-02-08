package au.id.ajlane.annotations;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

public abstract class ElementVisitors
{
    public static ElementVisitor<ExecutableElement, ExecutableElement> executableOrParam()
    {
        return new AbstractElementVisitor<ExecutableElement, ExecutableElement>()
        {
            @Override
            public ExecutableElement visitExecutable(
                final ExecutableElement element,
                final ExecutableElement defaultElement
            )
            {
                return element;
            }

            @Override
            protected ExecutableElement visitOther(final Element element, final ExecutableElement defaultElement)
            {
                return defaultElement;
            }
        };
    }

    public static <P> ElementVisitor<Name, P> qualifiedName()
    {
        return new AbstractElementVisitor<Name, P>()
        {
            @Override
            public Name visitType(final TypeElement element, final P param)
            {
                return element.getQualifiedName();
            }

            @Override
            protected Name visitOther(final Element element, final P param)
            {
                return null;
            }
        };
    }

    private ElementVisitors()
    throws InstantiationException
    {
        throw new InstantiationException();
    }
}
