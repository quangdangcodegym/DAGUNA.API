package com.cg.spblaguna.model.enumeration;

public enum EBookingServiceType {
    SCAR(1L,"Booking service scar"), SSPA(2L,"Booking service spa");
    private Long id;
    private String name;
    private EBookingServiceType(Long id, String name){
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
