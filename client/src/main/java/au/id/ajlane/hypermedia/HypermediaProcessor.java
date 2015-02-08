package au.id.ajlane.hypermedia;

import au.id.ajlane.annotations.AbstractTypeVisitor;
import au.id.ajlane.annotations.ElementVisitors;
import au.id.ajlane.codemodel.ToJTypeTypeVisitor;
import au.id.ajlane.properties.Properties;
import au.id.ajlane.properties.PropertyOrder;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JPrimitiveType;
import com.sun.codemodel.JType;
import com.sun.codemodel.JTypeVar;
import com.sun.codemodel.JVar;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HypermediaProcessor
    extends AbstractProcessor
{

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        return Collections.singleton(Link.class.getCanonicalName());
    }

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotationTypeElements, final RoundEnvironment roundEnv
    )
    {

        final Set<TypeElement> typeElements = roundEnv.getElementsAnnotatedWith(Hypermedia.class)
                                                      .parallelStream()
                                                      .map(e -> e instanceof TypeElement ? (TypeElement) e : null)
                                                      .filter(e -> e != null)
                                                      .collect(Collectors.toSet());

        final JCodeModel codeModel = new JCodeModel();
        for (final TypeElement typeElement : typeElements)
        {
            final Map<String, ExecutableElement> properties = new LinkedHashMap<>();
            final PropertyOrder propertyOrder = typeElement.getAnnotation(PropertyOrder.class);
            if (propertyOrder != null)
            {
                for (final String propertyName : propertyOrder.value())
                {
                    properties.put(propertyName, null);
                }
            }
            final Map<String, Link> links = new HashMap<>(properties.size());
            final Map<String, Action> actions = new HashMap<>(properties.size());
            Map<String, Id> ids = Collections.emptyMap();
            for (final Element enclosedElement : typeElement.getEnclosedElements())
            {
                final ExecutableElement methodElement = enclosedElement.accept(
                    ElementVisitors.executableOrParam(), null
                );

                if (methodElement != null)
                {
                    final String name = Properties.fieldName(
                        methodElement.getSimpleName()
                                     .toString()
                    );
                    properties.put(name, methodElement);
                    final Link link = methodElement.getAnnotation(Link.class);
                    if (link != null)
                    {
                        links.put(name, link);
                    }
                    final Action action = methodElement.getAnnotation(Action.class);
                    if (action != null)
                    {
                        if (link != null)
                        {
                            processingEnv.getMessager()
                                         .printMessage(
                                             Diagnostic.Kind.ERROR,
                                             "@Link and @Action cannot be applied to the same method.",
                                             typeElement
                                         );
                        }

                        actions.put(name, action);
                    }
                    final Id id = methodElement.getAnnotation(Id.class);
                    if (id != null)
                    {
                        if (!ids.isEmpty())
                        {
                            processingEnv.getMessager()
                                         .printMessage(
                                             Diagnostic.Kind.ERROR,
                                             "@Id can only be applied to one method per class.",
                                             typeElement
                                         );
                        }
                        else
                        {
                            ids = Collections.singletonMap(name, id);
                        }
                    }
                }
            }
            final String idPropertyName =
                !ids.isEmpty() ?
                    ids.keySet()
                       .iterator()
                       .next() :
                    null;

            final JDefinedClass dataClass = defineClass(
                codeModel,
                typeElement.getQualifiedName()
                           .toString() + "Data"
            );
            final JDefinedClass clientClass = defineClass(
                codeModel,
                typeElement.getQualifiedName()
                           .toString() + "Client"
            );

            final Map<String, JTypeVar> typeParams =
                typeElement.getTypeParameters()
                           .parallelStream()
                           .map(
                               e -> clientClass.generify(
                                   e.getSimpleName()
                                    .toString()
                               )
                           )
                           .collect(
                               Collectors.toMap(
                                   JTypeVar::fullName,
                                   Function.identity()
                               )
                           );

            implement(clientClass, typeElement, typeParams::get);

            final JMethod clientConstructor = clientClass.constructor(JMod.PUBLIC);
            final JFieldVar clientData = finalField(clientClass, clientConstructor, dataClass, "data");
            final JMethod clientDataGetter = getter(clientClass, clientData);

            final JMethod dataConstructor = dataClass.constructor(JMod.PUBLIC);

            for (final Map.Entry<String, ExecutableElement> property : properties.entrySet())
            {
                final String propertyName = property.getKey();
                final ExecutableElement methodElement = property.getValue();
                if (methodElement != null)
                {
                    final String methodName = methodElement.getSimpleName()
                                                           .toString();
                    final TypeMirror methodType = methodElement.getReturnType();
                    final Map<String, JTypeVar> methodTypeParams =
                        methodElement.getTypeParameters()
                                     .parallelStream()
                                     .map(
                                         e -> clientClass.generify(
                                             e.getSimpleName()
                                              .toString()
                                         )
                                     )
                                     .collect(
                                         Collectors.toMap(
                                             JTypeVar::fullName,
                                             Function.identity()
                                         )
                                     );
                    final Function<String, JTypeVar> effectiveTypeParams = t -> {
                        final JTypeVar methodParam = methodTypeParams.get(t);
                        if (methodParam != null)
                        {
                            return methodParam;
                        }
                        return typeParams.get(t);
                    };
                    final ToJTypeTypeVisitor toJTypeTypeVisitor = new ToJTypeTypeVisitor(effectiveTypeParams);
                    final Function<TypeMirror, JType> toJType = t -> t.accept(toJTypeTypeVisitor, codeModel);
                    final Function<TypeMirror, JType> toClientJType = t -> t.accept(
                        new AbstractTypeVisitor<JType, JCodeModel>()
                        {

                            @Override
                            public JType visitPrimitive(final PrimitiveType type, final JCodeModel codeModel)
                            {
                                return JPrimitiveType.parse(codeModel, type.toString());
                            }

                            @Override
                            public JType visitDeclared(final DeclaredType type, final JCodeModel codeModel)
                            {
                                final Hypermedia hypermedia = type.asElement()
                                                                  .getAnnotation(Hypermedia.class);
                                JClass ref = codeModel.ref(
                                    type.asElement()
                                        .toString() + (hypermedia != null ? "Client" : "")
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
                                return effectiveTypeParams.apply(type.toString());
                            }
                        },
                        codeModel
                    );

                    final Link link = links.get(propertyName);
                    final Action action = actions.get(propertyName);
                    final Id id = ids.get(propertyName);

                    if (id != null)
                    {
                        final JFieldVar dataField = finalField(
                            dataClass,
                            dataConstructor,
                            toJType.apply(methodType),
                            propertyName
                        );

                        final JMethod clientMethod = clientClass.method(
                            JMod.PUBLIC,
                            toClientJType.apply(methodType),
                            methodName
                        );
                        clientMethod.annotate(Override.class);
                        clientMethod.body()
                                    ._return(
                                        JExpr._this()
                                             .ref(clientData)
                                             .invoke(methodName)
                                    );

                        final JMethod dataGetter = getter(dataClass, dataField);

                        final JMethod clientRefresher = clientClass.method(
                            JMod.PUBLIC,
                            clientClass,
                            "refresh"
                        );
                        clientRefresher.body()
                                       .directStatement(
                                           "// GET String.valueOf(this." + clientData.name() + "." + methodName + "())"
                                       );
                        clientRefresher.body()
                                       ._return(JExpr._null());
                    }
                    else if (link != null)
                    {
                        final JFieldVar dataField = finalField(
                            dataClass,
                            dataConstructor,
                            String.class,
                            propertyName + "Url"
                        );
                        final JMethod clientMethod = clientClass.method(
                            JMod.PUBLIC,
                            String.class,
                            methodName + "Url"
                        );
                        clientMethod.body()
                                    ._return(
                                        JExpr._this()
                                             .ref(clientData)
                                             .invoke(methodName + "Url")
                                    );
                        final JMethod dataGetter = getter(dataClass, dataField);

                        final JMethod clientNavigator = clientClass.method(
                            JMod.PUBLIC,
                            toClientJType.apply(methodType),
                            methodName
                        );
                        clientNavigator.annotate(Override.class);
                        clientNavigator.body()
                                       .directStatement(
                                           "// GET this." + clientData.name() + "." + propertyName + "Url"
                                       );
                        clientNavigator.body()
                                       ._return(JExpr._null());
                    }
                    else if (action != null)
                    {
                        final JMethod clientMethod = clientClass.method(
                            JMod.PUBLIC,
                            toJType.apply(methodType),
                            methodName
                        );
                        clientMethod.annotate(Override.class);
                        clientMethod.body()
                                    .directStatement("// " + action.method() + " this." + idPropertyName + "Url");
                        clientMethod.body()
                                    ._return(JExpr._null());
                    }
                    else
                    {
                        final JFieldVar dataField = finalField(
                            dataClass,
                            dataConstructor,
                            toJType.apply(methodType),
                            propertyName
                        );

                        final JMethod clientGetter = clientClass.method(
                            JMod.PUBLIC,
                            toClientJType.apply(methodType),
                            methodName
                        );
                        clientGetter.annotate(Override.class);
                        clientGetter.body()
                                    ._return(
                                        JExpr._this()
                                             .ref(clientData)
                                             .invoke(methodName)
                                    );

                        final JMethod dataGetter = getter(dataClass, dataField);
                    }
                }
            }

        }

        save(codeModel);

        return true;
    }

    private JDefinedClass defineClass(final JCodeModel codeModel, final String fullyQualifiedName)
    {
        JDefinedClass _clientClass;
        try
        {
            _clientClass = codeModel._class(
                JMod.PUBLIC, fullyQualifiedName, ClassType.CLASS
            );
        } catch (final JClassAlreadyExistsException ex)
        {
            _clientClass = ex.getExistingClass();
        }
        return _clientClass;
    }

    private JFieldVar finalField(
        final JDefinedClass type,
        final JMethod constructor,
        final Class<?> fieldType,
        final String fieldName
    )
    {
        return finalField(
            type,
            constructor,
            type.owner()
                ._ref(fieldType),
            fieldName
        );
    }

    private JFieldVar finalField(
        final JDefinedClass type,
        final JMethod constructor,
        final JType fieldType,
        final String fieldName
    )
    {
        final JFieldVar field = type.field(
            JMod.PRIVATE | JMod.FINAL, fieldType, fieldName
        );
        final JVar param = constructor.param(
            JMod.FINAL,
            field.type(),
            field.name()
        );
        constructor.body()
                   .assign(
                       JExpr._this()
                            .ref(field),
                       param
                   );
        return field;
    }

    private JMethod getter(final JDefinedClass type, final JFieldVar field)
    {
        final JCodeModel codeModel = type.owner();
        final JMethod method = type.method(
            JMod.PUBLIC,
            field.type(),
            Properties.getterName(
                field.name(),
                field.type()
                     .equals(codeModel.BOOLEAN)
            )
        );
        method.body()
              ._return(
                  JExpr._this()
                       .ref(field)
              );
        return method;
    }

    private void implement(
        final JDefinedClass clientClass, final TypeElement typeElement, final Function<String, JTypeVar> typeParams
    )
    {
        JClass parentRef = clientClass.owner()
                                      .ref(
                                          typeElement.getQualifiedName()
                                                     .toString()
                                      );
        for (final TypeParameterElement arg : typeElement.getTypeParameters())
        {
            parentRef = parentRef.narrow(ToJTypeTypeVisitor.visit(typeParams, arg.asType(), clientClass.owner()));
        }
        clientClass._implements(parentRef);
    }

    private void save(final JCodeModel codeModel)
    {
        try
        {
            codeModel.build(
                new CodeWriter()
                {
                    public OutputStream output;

                    @Override
                    public OutputStream openBinary(final JPackage pkg, final String name)
                    throws IOException
                    {
                        final String simpleName = name.substring(0, name.length() - 5);
                        final String fullyQualifiedName = pkg.isUnnamed() ? simpleName : pkg.name() + "." + simpleName;
                        return this.output = processingEnv.getFiler()
                                                          .createSourceFile(fullyQualifiedName)
                                                          .openOutputStream();
                    }

                    @Override
                    public void close()
                    throws IOException
                    {
                        if (output != null)
                        {
                            output.close();
                        }
                    }
                }
            );
        } catch (final IOException e)
        {
            final StringWriter messageBuffer = new StringWriter();
            try (final PrintWriter messageWriter = new PrintWriter(messageBuffer))
            {
                messageWriter.println("Could not write generated sources:");
                e.printStackTrace(messageWriter);
                processingEnv.getMessager()
                             .printMessage(Diagnostic.Kind.ERROR, messageBuffer.toString());
            }

        }
    }

}
