package com.cg.spblaguna.model.enumeration;


public enum EDepositedStatus {
    ACCOMPLISHED(1L,"Accomplished"), UNFINISHED(2L,"Unfinished");
    private Long id;
    private String name;
    private EDepositedStatus(Long id, String name){
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
