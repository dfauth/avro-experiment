package com.github.dfauth.avro.renderer;

import com.github.dfauth.avro.model.Model;

import java.io.File;

public class JavaRenderer implements Renderer {
    public JavaRenderer(String basePath) {
        File f = new File(basePath);
        if(!f.exists()) {
            f.mkdirs();
        }
    }

    @Override
    public void render(Model m) {

    }

    private static String TEMPLATE = "${package}\n" +
            "\n" +
            "${imports}\n" +
            "\n" +
            "public class ${name} ${extends} {\n" +
            "\n" +
            "    ${fields}\n" +
            "\n" +
            "    ${setters}\n" +
            "\n" +
            "    ${getters}\n" +
            "}\n";

    /**
     *
     ${package}

     ${imports}

     public class ${name} ${extends} {

        ${fields}

        ${setters}

        ${getters}
     }


     *
     */
}
