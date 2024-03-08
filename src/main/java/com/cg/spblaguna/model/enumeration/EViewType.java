package com.cg.spblaguna.model.enumeration;

public enum EViewType {
    GARDEN_VIEW(1L,"Garden View"), SEA_VIEW(2L,"Sea View");
    private Long id;
    private String name;
    private EViewType(Long id, String name){
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
