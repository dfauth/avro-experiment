package com.github.dfauth.avro.model;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Model {

    private static final Logger logger = LoggerFactory.getLogger(Model.class);
    private final Schema schema;

    public Model(Schema s) {
        this.schema = s;
    }
}
