package com.fundamentos.springboot.fundamentos.bean;

public class MyBeanWithDependendyImplement  implements  MyBeanWithDependency{
    private MyOperation myOperation;

    public MyBeanWithDependendyImplement(MyOperation myOperation){
        this. myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        int numero=1;
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola desde la implementacion de un bean con dependencia");
    }
}
