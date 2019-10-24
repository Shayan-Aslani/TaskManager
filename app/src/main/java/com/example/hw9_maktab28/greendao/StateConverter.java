package com.example.hw9_maktab28.greendao;

import com.example.hw9_maktab28.model.Role;
import com.example.hw9_maktab28.model.State;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.converter.PropertyConverter;

public class StateConverter implements PropertyConverter<State, Integer> {
    @Override
    public State convertToEntityProperty(Integer databaseValue) {
        for(State state:State.values()){
            if(state.getI() == databaseValue)
                return state;
        }
        return null;
    }

    @Override
    public Integer convertToDatabaseValue(State entityProperty) {
        return entityProperty.getI();
    }
}
