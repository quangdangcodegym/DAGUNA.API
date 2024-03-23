package com.cg.spblaguna.model.enumeration;

public enum ECardType {
    MASTERCARD(1L,"Mastercard"), VISA(2L,"Visa");
    private Long id;
    private String name;
    private ECardType(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
