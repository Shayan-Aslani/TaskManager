package com.example.hw9_maktab28.model;

public enum Role {
    USER(0) ,
    ADMIN (1);

    private int i ;

    Role(int i ){
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
