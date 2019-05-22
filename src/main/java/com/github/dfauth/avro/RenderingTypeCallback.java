package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;


public class RenderingTypeCallback implements TypeCallback<Renderer> {

    private static final Logger logger = LoggerFactory.getLogger(RenderingTypeCallback.class);

    @Override
    public com.github.dfauth.avro.Renderer recordType(Schema s) {
        return new RecordTypeRenderer(s);
    }

    @Override
    public com.github.dfauth.avro.Renderer mapType(Schema s) {
        throw new UnsupportedOperationException("Map type not supported for rendering");
    }

    @Override
    public com.github.dfauth.avro.Renderer arrayType(Schema s) {
        throw new UnsupportedOperationException("Array type not supported for rendering");
    }

    @Override
    public com.github.dfauth.avro.Renderer unionType(Schema s) {
        throw new UnsupportedOperationException("Union type not supported for rendering");
    }

    @Override
    public com.github.dfauth.avro.Renderer fixedType(Schema s) {
        return new FixedTypeRender(s);
    }

    @Override
    public com.github.dfauth.avro.Renderer simpleType(Schema s) {
        throw new UnsupportedOperationException("Simple types not supported for rendering");
    }


    private class FixedTypeRender implements com.github.dfauth.avro.Renderer {
        public FixedTypeRender(Schema s) {
        }

        @Override
        public void render(CompilationCustomization c, Schema s, OutputStream ostream) {
            Writer writer = new OutputStreamWriter(ostream);
            Set<String> imports = Collections.emptySet();
//            Set<String> constructorArguments = getFields(s);
//            String value = String.format(TEMPLATE, getNamespace(s), renderImports(imports), getName(s), renderContructors(constructorArguments));
//            writer.write(value);
//            writer.flush();
//            writer.close();
        }
    }

    private class RecordTypeRenderer implements Renderer {
        public RecordTypeRenderer(Schema s) {
        }

        @Override
        public void render(CompilationCustomization c, Schema s, OutputStream ostream) {
            try {
                Writer writer = new OutputStreamWriter(ostream);
                Set<String> imports = Collections.emptySet();
                Set<String> constructorArguments = getFields(s);
                String value = String.format(TEMPLATE, c.getNamespace(s), renderImports(imports), c.getName(s), renderContructors(constructorArguments));
                writer.write(value);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        private Set<String> getFields(Schema s) {
            return s.getFields().stream().map(f -> {
                // f.schema().getNamespace() + "." + f.schema().getName()
                FunctionTypeCallback<String> tmp = new FunctionTypeCallback<String>(s1 -> {
                    return s1.getNamespace() + "." + s1.getName();
                }){
                    @Override
                    public String simpleType(Schema s) {
                        return "String";
                    }
                    @Override
                    public String unionType(Schema s) {
                        return "T";
                    }
                    @Override
                    public String fixedType(Schema s) {
                        return "Enum";
                    }
                    @Override
                    public String arrayType(Schema s) {
                        return "Array";
                    }
                };
                return TypeCallback.typeOf(f.schema(), tmp);
            }).collect(Collectors.toSet());
//        AbstractTypeCallback<Set<String>> tmp = new AbstractTypeCallback<Set<String>>(s1 -> {
//            return s1.getFields().stream().map(f -> f.schema().getNamespace() + "." + f.schema().getName()).collect(Collectors.toSet());
//        }){
//            @Override
//            public Set<String> acceptSimpleSchema(Schema s) {
//                return s.getFields().stream().map(f -> "String").collect(Collectors.toSet());
//            }
//        };
//        return typeOf(s, tmp);
        }

        private String renderContructors(Set<String> constructorArguments) {
            StringWriter sw = new StringWriter();
            Iterator<String> it = constructorArguments.iterator();
            while(it.hasNext()) {
                sw.append(it.next());
                if(it.hasNext()) {
                    sw.append(", ");
                }
            }
            return sw.toString();
        }

        private String renderImports(Set<String> imports) {
            return "\n";
        }

        private static final String TEMPLATE = "package %s" +
                "" +
                "%s" +
                "" +
                "case class %s(%s)";

    }
}
