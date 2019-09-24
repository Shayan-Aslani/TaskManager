package com.example.hw9_maktab28.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public enum State implements Serializable {
    Todo , Doing , Done;

    private final static String todoName = "Todo" ;
    private final static String doingName = "Doing" ;
    private final static String doneName = "Done" ;

    @NonNull
    @Override
    public String toString() {
        return this.name();
    }

    public static State getStateFromString(String name)
    {
        switch (name){
            case todoName :
                return State.Todo;
            case doingName :
                return State.Doing;
            case doneName :
                return State.Done;

        }
        return null;
    }
}
