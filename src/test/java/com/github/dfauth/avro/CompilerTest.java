package com.github.dfauth.avro;

import com.github.dfauth.avro.model.*;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.github.dfauth.avro.TypeCallback.typeOf;


public class CompilerTest {

    private static final Logger logger = LoggerFactory.getLogger(CompilerTest.class);

    @Test
    public void testIt() {
        CompilationCustomization customization = new CompilationCustomization() {
            @Override
            public void render(Schema s, OutputStream ostream) {
                Renderer renderer = typeOf(s, new RenderingTypeCallback());
                renderer.render(this, s, ostream);
            }

        };
        CompilerSchemaHandler handler = new CompilerSchemaHandler(s -> {
            logger.info("process schema: "+s);
            try {
                OutputStream ostream = customization.getOutputStream(s);
                customization.render(s, ostream);
                ostream.flush();
                ostream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
        new SchemaProcessor(handler).process(AvroSchemaBuilder.createSchema());
    }

    @Test
    public void testIt2() {
        Map<Schema, Model> modelMap = new HashMap<>();
        CompilerSchemaHandler handler = new CompilerSchemaHandler(s -> {
            logger.info("process schema: "+s);
            Model model = typeOf(s, new TypeCallback<Model>() {
                @Override
                public Model recordType(Schema s) {
                    return new RecordModel(s);
                }

                @Override
                public Model mapType(Schema s) {
                    return new MapModel(s);
                }

                @Override
                public Model arrayType(Schema s) {
                    return new ArrayModel(s);
                }

                @Override
                public Model unionType(Schema s) {
                    return new UnionModel(s);
                }

                @Override
                public Model enumType(Schema s) {
                    return new EnumModel(s);
                }

                @Override
                public Model simpleType(Schema s) {
                    return new PrimitiveModel(s);
                }
            });
            modelMap.put(s, model);
        });
        new SchemaProcessor(handler).process(AvroSchemaBuilder.createSchema());
    }

}
