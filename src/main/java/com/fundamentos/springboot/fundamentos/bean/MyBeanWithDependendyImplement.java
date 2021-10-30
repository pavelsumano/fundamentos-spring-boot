package com.fundamentos.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependendyImplement  implements  MyBeanWithDependency{

    Log LOGGER = LogFactory.getLog(MyBeanWithDependendyImplement.class);

    private MyOperation myOperation;

    public MyBeanWithDependendyImplement(MyOperation myOperation){
        this. myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("dentro del metodo printWithDependency ");
        int numero=1;
        LOGGER.debug("el numero enviado es: "+numero);
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola desde la implementacion de un bean con dependencia");
    }
}
