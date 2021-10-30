package com.fundamentos.springboot.fundamentos.bean;

public class MyBeanWithPropertiesImplement implements MyBeanWithProperties {
    private String nombre;
    private String app;

    public MyBeanWithPropertiesImplement(String nombre, String app){
        this.nombre = nombre;
        this.app = app;
    }

    @Override
    public String function(){
        return nombre+" - "+app;
    }
}
