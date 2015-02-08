package au.id.ajlane.annotations;

import javax.lang.model.element.Name;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

public abstract class TypeVisitors
{
    public static <P> TypeVisitor<Name, P> qualifiedName()
    {
        return new AbstractTypeVisitor<Name, P>()
        {
            @Override
            public Name visitDeclared(final DeclaredType type, final P param)
            {
                return type.asElement()
                           .accept(ElementVisitors.qualifiedName(), param);
            }

            @Override
            protected Name visitOther(final TypeMirror type, final P param)
            {
                return null;
            }
        };
    }

    ;

    private TypeVisitors()
    throws InstantiationException
    {
        throw new InstantiationException();
    }
}
