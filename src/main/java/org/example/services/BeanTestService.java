package org.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanTestService {

    private final String conditionalBean;

    @Autowired
    public BeanTestService(String thisIsMyFirstConditionalBean) {
        this.conditionalBean = thisIsMyFirstConditionalBean;
    }

    public void printConditionalBean() {
        System.out.println("Injected Bean: " + conditionalBean);
    }
}