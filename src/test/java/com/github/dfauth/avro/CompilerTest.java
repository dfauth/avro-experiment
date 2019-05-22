package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.OutputStream;

import static com.github.dfauth.avro.TypeCallback.typeOf;


public class CompilerTest {

    private static final Logger logger = LoggerFactory.getLogger(CompilerTest.class);

    @Test
    public void testIt() {
        new SchemaProcessor(new CompilerSchemaHandler(new CompilationCustomization() {
            @Override
            public void render(Schema s, OutputStream ostream) {
                Renderer renderer = typeOf(s, new RenderingTypeCallback());
                renderer.render(this, s, ostream);
            }

        })).process(AvroSchemaBuilder.createSchema());
    }

}
