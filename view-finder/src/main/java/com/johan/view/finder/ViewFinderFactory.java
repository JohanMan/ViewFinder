package com.johan.view.finder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by johan on 2018/9/10.
 */

public class ViewFinderFactory {

    /**
     * 创建 ViewFinder
     * @param target
     * @param <F>
     * @return
     */
    public static <F> F create(Object target) {
        Type genType = target.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if(params.length < 1){
            throw new RuntimeException(target.getClass().getName() + " ViewFinder Type Unclear !!");
        }
        Class<F> finderClass = (Class<F>) params[0];
        try {
            return finderClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(target.getClass().getName() + " ViewFinder Create Fail !!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(target.getClass().getName() + " ViewFinder Create Fail !!");
        }
    }

}
