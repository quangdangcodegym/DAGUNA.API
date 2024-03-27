package com.cg.spblaguna.model.enumeration;

public enum EPrefix {
    MR(1L,"Mr"), MISS(2L,"Mss");
    private Long id;
    private String name;
    private EPrefix(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
