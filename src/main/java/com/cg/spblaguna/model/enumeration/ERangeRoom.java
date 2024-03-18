package com.cg.spblaguna.model.enumeration;

public enum ERangeRoom {

    A(1L,"A"),B(2L,"B"),C(3L,"C"),D(4L,"D");

    private Long id;
    private String name;
    private ERangeRoom(Long id, String name){
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
