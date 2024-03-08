package com.cg.spblaguna.model.enumeration;

public enum EMethod {
    CARD(1L, "Card"), TRANSFER(2L, "Transfer");
    private Long id;
    private String name;
    private EMethod(Long id, String name){
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
