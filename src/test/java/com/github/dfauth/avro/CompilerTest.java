package com.github.dfauth.avro;

import com.github.dfauth.avro.model.*;
import com.github.dfauth.avro.renderer.Renderer;
import com.github.dfauth.avro.renderer.JavaRenderer;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class CompilerTest {

    private static final Logger logger = LoggerFactory.getLogger(CompilerTest.class);

    @Test
    public void testIt() {
        CompilationCustomization customization = new CompilationCustomization() {
            @Override
            public void render(Schema s, OutputStream ostream) {
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
            modelMap.put(s, AvroTypeModelFactory.typeOf(s));
        });
        new SchemaProcessor(handler).process(AvroSchemaBuilder.createSchema());

        modelMap.values().forEach(m -> {
            m.resolve(schema -> modelMap.get(schema));
        });

        Renderer renderer = new JavaRenderer("target/generated");
        modelMap.values().forEach(m -> {
            renderer.render(m);
        });

    }

}
