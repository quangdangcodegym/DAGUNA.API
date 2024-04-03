package com.cg.spblaguna.model.enumeration;

public enum ERole {

    ROLE_CUSTOMER(1L, "Customer"), ROLE_RECEPTIONIST(2L,"Receptionist"), ROLE_ADMIN(3L, "Admin");

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
