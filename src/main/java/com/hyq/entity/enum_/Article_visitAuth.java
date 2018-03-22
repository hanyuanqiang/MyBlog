package com.hyq.entity.enum_;

public enum Article_visitAuth {

    仅自己可见("onlyMyself"),所有人可见("all");

    private String name;

    Article_visitAuth(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
