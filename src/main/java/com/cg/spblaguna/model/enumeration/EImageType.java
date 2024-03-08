package com.cg.spblaguna.model.enumeration;

public enum EImageType {
    ROOM(1L,"Room"),RECEPTIONIST(2L,"Receptionist");
    private Long id;
    private String name;
    private EImageType(Long id, String name){
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
