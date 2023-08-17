package com.snuh.smile.util;

public enum AuthLogEnum {

        create("create"), delete("delete"),recreate("recreate");

        private final String value;

        AuthLogEnum(String value){ this.value = value; }
        public String getValue(){ return value; }


}
