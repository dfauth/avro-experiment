package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public interface CompilationCustomization {

    Logger logger = LoggerFactory.getLogger(CompilationCustomization.class);

    default OutputStream getOutputStream(Schema s) {
        try {
            File f = new File(toFilePath(getNamespace(s)));
            f.mkdirs();
            f = new File(toFilePath(getNamespace(s), getName(s)));
            return new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    default String toFilePath(String namespace) {
        return namespace.replace('.', '/');
    }

    default String toFilePath(String namespace, String name) {
        return toFilePath(namespace)+"/"+name+".java";
    }

    default String getName(Schema s) {
        return s.getName();
    }

    default String getNamespace(Schema s) {
        return s.getNamespace();
    }

    void render(Schema s, OutputStream ostream);
}
