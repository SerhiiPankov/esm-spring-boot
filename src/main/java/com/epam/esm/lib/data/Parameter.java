package com.epam.esm.lib.data;

public class Parameter {
    private String name;
    private String[] values;

    public Parameter(String name, String[] values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValues() {
        return values;
    }

}
