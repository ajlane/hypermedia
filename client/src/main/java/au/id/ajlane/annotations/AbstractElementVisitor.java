package au.id.ajlane.annotations;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class AbstractElementVisitor<R, P>
    implements ElementVisitor<R, P>
{
    @Override
    public R visit(final Element e, final P p)
    {
        return visitOther(e, p);
    }

    @Override
    public R visit(final Element e)
    {
        return visitOther(e, null);
    }

    @Override
    public R visitPackage(final PackageElement e, final P p)
    {
        return visitOther(e, p);
    }

    @Override
    public R visitType(final TypeElement e, final P p)
    {
        return visitOther(e, p);
    }

    @Override
    public R visitVariable(final VariableElement e, final P p)
    {
        return visitOther(e, p);
    }

    @Override
    public R visitExecutable(final ExecutableElement e, final P p)
    {
        return visitOther(e, p);
    }

    @Override
    public R visitTypeParameter(final TypeParameterElement e, final P p)
    {
        return visitOther(e, p);
    }

    @Override
    public R visitUnknown(final Element e, final P p)
    {
        return visitOther(e, p);
    }

    protected R visitOther(final Element e, final P p)
    {
        throw new UnsupportedOperationException(e.toString() + " is not supported.");
    }
}
