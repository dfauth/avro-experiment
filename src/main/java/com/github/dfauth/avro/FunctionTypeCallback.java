package com.github.dfauth.avro;

import org.apache.avro.Schema;

import java.util.function.Function;


public class FunctionTypeCallback<T> implements TypeCallback<T> {

    private final Function<Schema, T> f;

    public FunctionTypeCallback(Function<Schema, T> f) {
        this.f = f;
    }

    @Override
    public T recordType(Schema s) {
        return f.apply(s);
    }

    @Override
    public T mapType(Schema s) {
        return f.apply(s);
    }

    @Override
    public T arrayType(Schema s) {
        return f.apply(s);
    }

    @Override
    public T unionType(Schema s) {
        return f.apply(s);
    }

    @Override
    public T fixedType(Schema s) {
        return f.apply(s);
    }

    @Override
    public T simpleType(Schema s) {
        return f.apply(s);
    }
}
