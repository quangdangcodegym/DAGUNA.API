package com.cg.spblaguna.model.enumeration;

public enum EStatusUser {
    ACTIVE(1L,"Active"), BLOCK(2L,"Block"), PENDING(3L,"Pending");
    private Long id;
    private String name;
    private EStatusUser(Long id, String name){
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
