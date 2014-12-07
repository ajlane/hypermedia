package au.id.ajlane.hypermedia;

import au.id.ajlane.annotations.AbstractElementVisitor;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class NavigatorProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Link.class.getCanonicalName());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotationTypeElements,
                           final RoundEnvironment roundEnv) {

        final Set<TypeElement>
            typeElements =
            new TreeSet<>((a, b) -> a.getQualifiedName().toString()
                .compareTo(b.getQualifiedName().toString()));

        for (final Element annotatedElement : roundEnv.getElementsAnnotatedWith(Link.class)) {
            final TypeElement typeElement = (TypeElement) annotatedElement.getEnclosingElement();
            typeElements.add(typeElement);
        }

        final JCodeModel codeModel = new JCodeModel();
        for (final TypeElement typeElement : typeElements) {
            final String
                fullyQualifiedName =
                typeElement.getQualifiedName().toString() + "Navigator";

            JDefinedClass navigatorClass;
            try {
                navigatorClass = codeModel._class(
                    JMod.PUBLIC,
                    fullyQualifiedName,
                    ClassType.CLASS
                );
            } catch (final JClassAlreadyExistsException ex) {
                navigatorClass = ex.getExistingClass();
            }

            for (final Element enclosedElement : typeElement.getEnclosedElements()) {
                final ExecutableElement linkMethodElement = enclosedElement.accept(
                    new AbstractElementVisitor<ExecutableElement, JCodeModel>() {
                        @Override
                        public ExecutableElement visitExecutable(final ExecutableElement e,
                                                                 final JCodeModel jCodeModel) {
                            if (e.getAnnotation(Link.class) != null) {
                                return e;
                            }
                            return null;
                        }

                        @Override
                        protected ExecutableElement visitOther(final Element e, final JCodeModel codeModel) {
                            return null;
                        }
                    }, codeModel);

                if (linkMethodElement != null) {
                    final JMethod method = navigatorClass.method(
                        JMod.PUBLIC | JMod.STATIC,
                        InputStream.class,
                        linkMethodElement.getSimpleName().toString()
                    );
                    method.body()._return(JExpr._null());
                }
            }

        }
        try {
            codeModel.build(new CodeWriter() {
                public OutputStream output;

                @Override
                public void close() throws IOException {
                    if (output != null) {
                        output.close();
                    }
                }

                @Override
                public OutputStream openBinary(final JPackage pkg, final String name)
                    throws IOException {
                    final String simpleName = name.substring(0, name.length() - 5);
                    final String fullyQualifiedName =
                        pkg.isUnnamed() ?
                        simpleName :
                        pkg.name() + "." + simpleName;
                    return
                        this.output =
                            processingEnv.getFiler().createSourceFile(fullyQualifiedName)
                                .openOutputStream();
                }
            });
        } catch (final IOException e) {
            final StringWriter messageBuffer = new StringWriter();
            try (final PrintWriter messageWriter = new PrintWriter(messageBuffer)) {
                messageWriter.println("Could not write generated sources:");
                e.printStackTrace(messageWriter);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, messageBuffer.toString());
            }

        }

        return true;
    }
}
