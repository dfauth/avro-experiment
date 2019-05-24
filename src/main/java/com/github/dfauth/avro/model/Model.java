package com.github.dfauth.avro.model;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Model {

    private static final Logger logger = LoggerFactory.getLogger(Model.class);
    protected final Schema schema;

    public Model(Schema s) {
        this.schema = s;
    }

    public abstract void resolve(Resolver resolver);
}
