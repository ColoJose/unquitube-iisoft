package com.iisoft.unquitube.backend.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GeneralConverter<T, DT> {
    // PARA LA PROXIMA HACER ESTE
    /*public List<T> convertUsers(List<DT> dtos){
        List<T> list = new ArrayList<>();

        dtos.forEach(u -> list.add(this.getInstanceOfT());

        return list;
    }

    private T getInstanceOfT(Class<T> aClass, Class<DT> dt, DT dtObject) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = aClass.getConstructor(dt);
        return (T) constructor.newInstance(dtObject);
    }*/
}
