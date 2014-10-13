package au.id.ajlane.hypermedia;

import au.id.ajlane.annotations.AbstractElementVisitor;
import com.sun.codemodel.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;

public class HypermediaProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Hypermedia.class.getCanonicalName());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotatedElements,
                           final RoundEnvironment roundEnv) {

        final JCodeModel codeModel = new JCodeModel();
        for (final TypeElement annotatedElement : annotatedElements) {
            final JDefinedClass clientClass = annotatedElement.accept(new AbstractElementVisitor<JDefinedClass, JCodeModel>() {
                @Override
                public JDefinedClass visitType(final TypeElement e, final JCodeModel codeModel) {
                    try {
                        return codeModel._class(JMod.PUBLIC, e.getSimpleName() + "Client", ClassType.CLASS);
                    } catch (final JClassAlreadyExistsException e1) {
                        return e1.getExistingClass();
                    }
                }
            }, codeModel);

        }

        try {
            codeModel.build(new CodeWriter() {
                public OutputStream output;

                @Override
                public OutputStream openBinary(final JPackage pkg, final String name) throws IOException {
                    return this.output = processingEnv.getFiler().createSourceFile(pkg.name() + "." + name).openOutputStream();
                }

                @Override
                public void close() throws IOException {
                    if (output != null) output.close();
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
