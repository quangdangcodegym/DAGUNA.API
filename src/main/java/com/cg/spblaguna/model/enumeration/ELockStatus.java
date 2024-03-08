package com.cg.spblaguna.model.enumeration;

public enum ELockStatus {
        LOCK(1L,"Lock"), UNLOCK(2L, "Unlock");
        private Long id;
        private String name;
        private ELockStatus(Long id, String name){
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
