package au.id.ajlane.codemodel;

import au.id.ajlane.annotations.AbstractTypeVisitor;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPrimitiveType;
import com.sun.codemodel.JType;
import com.sun.codemodel.JTypeVar;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import java.util.function.Function;

public class ToJTypeTypeVisitor
    extends AbstractTypeVisitor<JType, JCodeModel>
{

    public static JType accept(final TypeMirror type, final JCodeModel codeModel)
    {
        return visit(
            s -> {
                throw new UnsupportedOperationException();
            }, type, codeModel
        );
    }

    public static JType visit(
        final Function<String, JTypeVar> typeParams,
        final TypeMirror type,
        final JCodeModel codeModel
    )
    {
        return type.accept(new ToJTypeTypeVisitor(typeParams), codeModel);
    }

    private final Function<String, JTypeVar> typeParams;

    public ToJTypeTypeVisitor(final Function<String, JTypeVar> typeParams)
    {
        this.typeParams = typeParams;
    }

    @Override
    public JType visitPrimitive(final PrimitiveType type, final JCodeModel codeModel)
    {
        return JPrimitiveType.parse(codeModel, type.toString());
    }

    @Override
    public JType visitDeclared(final DeclaredType type, final JCodeModel codeModel)
    {
        JClass ref = codeModel.ref(
            type.asElement()
                .toString()
        );
        for (final TypeMirror typeArg : type.getTypeArguments())
        {
            ref = ref.narrow(typeArg.accept(this, codeModel));
        }
        return ref;
    }

    @Override
    public JType visitTypeVariable(final TypeVariable type, final JCodeModel codeModel)
    {
        return typeParams.apply(type.toString());
    }
}
