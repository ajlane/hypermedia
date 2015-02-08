package au.id.ajlane.annotations;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.AbstractTypeVisitor8;

public class AbstractTypeVisitor<R, P>
    extends AbstractTypeVisitor8<R, P>
{
    @Override
    public R visitIntersection(final IntersectionType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitPrimitive(final PrimitiveType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitNull(final NullType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitArray(final ArrayType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitDeclared(final DeclaredType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitError(final ErrorType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitTypeVariable(final TypeVariable type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitWildcard(final WildcardType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitExecutable(final ExecutableType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitNoType(final NoType type, final P param)
    {
        return visitOther(type, param);
    }

    @Override
    public R visitUnion(final UnionType type, final P param)
    {
        return visitOther(type, param);
    }

    protected R visitOther(final TypeMirror type, final P param)
    {
        throw new UnsupportedOperationException(
            type.getClass()
                .getSimpleName() + " is not supported."
        );
    }
}
