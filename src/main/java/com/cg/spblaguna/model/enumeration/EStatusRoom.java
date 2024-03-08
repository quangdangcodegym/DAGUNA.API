package com.cg.spblaguna.model.enumeration;

public enum EStatusRoom {
    PLACED(1L,"Placed"), READY(2L,"Ready"), NOT_READY(3L,"Not Ready");
    private Long id;
    private String name;
    private EStatusRoom(Long id, String name){
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
