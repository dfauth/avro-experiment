package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AvroSchemaBuilder {

    private static final String NAMESPACE = "com.github.dfauth.avro";

    public static Schema createSchema(){

        return Schema.createUnion(createUserSchema(), createCompanySchema(), createAuthContextSchema());
    }

    public static Schema createEventsSchema(){

        return Schema.createUnion(createEventSchema(), updateEventSchema());
    }

    public static Schema createEventSchema(){

        return SchemaBuilder.record("CreateEvent").namespace(NAMESPACE)
                .fields().
                        requiredString("source").
                endRecord();
    }

    public static Schema updateEventSchema(){

        return SchemaBuilder.record("UpdateEvent").namespace(NAMESPACE)
                .fields().
                        requiredString("source").
                endRecord();
    }

    public static Schema createAuthContextSchema(){

        return SchemaBuilder.record("AuthContext").namespace(NAMESPACE)
                .fields().
                        requiredString("token").
                        name("payload").type(createEventsSchema()).noDefault()
                .endRecord();
    }

    public static Schema createUserSchema(){

        return SchemaBuilder.record("User").namespace(NAMESPACE)
                .fields().
                        requiredLong("id").
                        requiredString("userName").
                        name("roles").type().array().items().type(createRoleSchema()).arrayDefault(Collections.emptyList()).
                        name("status").type(createEmploymentStatusSchema()).withDefault("Permanent")
                .endRecord();
    }

    public static Schema createCompanySchema(){

        return SchemaBuilder.record("Company").namespace(NAMESPACE)
                .fields().
                        requiredLong("id").
                        requiredString("name").
                        name("address").type(createAddressSchema()).noDefault()
                .endRecord();
    }

    public static Schema createAddressSchema(){

        return SchemaBuilder.record("Address").namespace(NAMESPACE)
                .fields().
                        name("number").type().intType().noDefault().
                        name("street").type().stringType().noDefault().
                        name("addressLine2").type().stringType().noDefault().
                        name("city").type().stringType().noDefault().
                        name("postalCode").type().stringType().noDefault().
                        name("country").type().stringType().noDefault()
                .endRecord();
    }

    private static Schema createEmploymentStatusSchema() {
        return Schema.createEnum("EmploymentStatus",null, NAMESPACE, toList("Permanent", "Contractor", "PS"));
    }

    private static List<String> toList(String... s) {
        return Arrays.stream(s).collect(Collectors.toList());
    }

    public static Schema createRoleSchema(){

        return SchemaBuilder.record("Role").namespace(NAMESPACE)
                .fields().
                        requiredLong("id").
                        requiredString("roleName").
                        name("description").type().stringType().noDefault()
                .endRecord();
    }
}