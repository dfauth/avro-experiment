package com.github.dfauth.avro;

import org.apache.avro.Schema;

import java.io.OutputStream;

public interface Renderer {
    void render(CompilationCustomization customization, Schema s, OutputStream ostream);
}
