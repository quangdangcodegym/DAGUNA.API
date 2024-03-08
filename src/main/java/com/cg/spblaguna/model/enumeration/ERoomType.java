package com.cg.spblaguna.model.enumeration;


public enum ERoomType {
    SUPERIOR(1L, "Superior"), SUPERIOR_PLUS(2L, "Superior plus"), HONEYMOON_BUNGALOW(3L, "Honeymoon bungalow");

    private Long id;
    private String name;
    private ERoomType(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
