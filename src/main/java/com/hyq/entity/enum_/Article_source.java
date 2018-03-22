package com.hyq.entity.enum_;

public enum Article_source {

    原创("original"),转载("reprint");

    private String name;

    Article_source(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
