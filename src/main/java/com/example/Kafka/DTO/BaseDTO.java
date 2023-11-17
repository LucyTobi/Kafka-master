package com.example.Kafka.DTO;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class BaseDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void copyDataTo(Object destination){
        BeanUtils.copyProperties(this, destination);
    }

    public void copyDataFrom(Object source){
        BeanUtils.copyProperties(source, this);
    }
}
