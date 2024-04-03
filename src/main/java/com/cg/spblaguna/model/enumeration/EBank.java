package com.cg.spblaguna.model.enumeration;

public enum EBank {
    ACB(1L, "ACB"), VIETTINBANK(2L, "VIETTINBANK"), VIETCOMBANK(3L, "VIETCOMBANK");
    private Long id;
    private String name;
    private EBank(Long id, String name){
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
