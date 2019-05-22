package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

import static com.github.dfauth.avro.TypeConsumer.typeOf;


public class CompilerSchemaHandler implements SchemaHandler {

    private static final Logger logger = LoggerFactory.getLogger(CompilerSchemaHandler.class);
    private final CompilationCustomization customization;

    private Queue<Schema> queue = new ArrayDeque<>();
    private Stack<Schema> stack = new Stack<>();

    public CompilerSchemaHandler(CompilationCustomization customization) {
        this.customization = customization;
    }

    @Override
    public void startNested(Optional<String> name, Schema schema) {
        enqueue(schema);
        stack.push(schema);
    }

    @Override
    public void endNested(Optional<String> name, Schema schema) {
        pop();
    }

    @Override
    public void onSimple(Optional<String> name, Schema schema) {
        enqueue(schema);
    }

    @Override
    public void startArray(Optional<String> name, Schema schema) {
        enqueue(schema);
        stack.push(schema);
    }

    @Override
    public void endArray(Optional<String> name, Schema schema) {
        pop();
    }

    @Override
    public void startUnion(Optional<String> name, Schema schema) {
        enqueue(schema);
        stack.push(schema);
    }

    @Override
    public void endUnion(Optional<String> name, Schema schema) {
        pop();
    }

    private void pop() {
        stack.pop();
        if(stack.isEmpty()) {
            stage2();
        }
    }

    /** Recursively enqueue schemas that need a class generated. */
    private void enqueue(Schema schema) {
        if (queue.contains(schema)) return;
        typeOf(schema, new TypeConsumer() {
            @Override
            public void acceptRecordSchema(Schema s) {
                queue.add(schema);
                for (Schema.Field field : schema.getFields())
                    enqueue(field.schema());
            }

            @Override
            public void acceptMapSchema(Schema s) {
                enqueue(schema.getValueType());
            }

            @Override
            public void acceptTypeSchema(Schema s) {
                enqueue(schema.getElementType());
            }

            @Override
            public void acceptUnionSchema(Schema s) {
                for (Schema s1 : schema.getTypes())
                    enqueue(s1);
            }

            @Override
            public void acceptFixedSchema(Schema s) {
                queue.add(schema);
            }
        });
    }

    private void stage2() {
        processQueue(s -> {
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
    }

    private void processQueue(Consumer<Schema> consumer) {
        Schema s;
        while((s = queue.poll()) != null) {
            try {
                consumer.accept(s);
            } catch(RuntimeException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }



}
