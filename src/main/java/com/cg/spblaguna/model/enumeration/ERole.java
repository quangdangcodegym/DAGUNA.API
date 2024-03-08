package com.cg.spblaguna.model.enumeration;

public enum ERole {

    CUSTOMER(1L, "Customer"), RECEPTIONIST(2L,"Receptionist"), ADMIN(3L, "Admin");

    private Long id;
    private String name;
    private ERole(Long id, String name){
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
