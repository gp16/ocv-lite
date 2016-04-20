/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;


import engine.Engine;
import engine.Methods;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Amr_Ayman
 */
public class Man {
    private Methods methods = new Methods();
    public void Man(String method_name) 
    {
        List<Method>methods=Engine.getInstance().getMethod(method_name);
        for (Method method : methods) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            System.out.println(Arrays.toString(parameterAnnotations));
                    }
    }}


