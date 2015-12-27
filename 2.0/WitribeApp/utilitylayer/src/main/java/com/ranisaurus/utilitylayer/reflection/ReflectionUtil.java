package com.ranisaurus.utilitylayer.reflection;


import android.view.View;

import com.ranisaurus.utilitylayer.logger.Log4a;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */
public class ReflectionUtil {
    // reflection utility methods

    public static String getToStringDescription(Class<?> cls, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append(": ");
        for (Field f : cls.getDeclaredFields()) {
            sb.append(f.getName());
            sb.append("=");
            try {
                sb.append(f.get(obj));
            } catch (IllegalAccessException | IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append(", ");
        }
        return sb.toString();
    }

    public static Object instantiate(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            Log4a.e("InstantiationException", e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            Log4a.e("IllegalAccessException", e.getLocalizedMessage());
        }
        return null;
    }

    public static Object instantiate(Class clazz, Class parmClasss, Object parmValue) {
        try {
            return clazz.getConstructor(parmClasss).newInstance(parmValue);
        } catch (InstantiationException e) {
            Log4a.e("InstantiationException", e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            Log4a.e("IllegalAccessException", e.getLocalizedMessage());
        } catch (NoSuchMethodException e) {
            Log4a.printException(e);
        } catch (InvocationTargetException e) {
            Log4a.printException(e);
        }
        return null;
    }

    public static Object instantiate(Class clazz, Object parmValue) {
        try {
            return clazz.getConstructor(View.class).newInstance(parmValue);
        } catch (InstantiationException e) {
            Log4a.e("InstantiationException", e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            Log4a.e("IllegalAccessException", e.getLocalizedMessage());
        } catch (NoSuchMethodException e) {
            Log4a.printException(e);
        } catch (InvocationTargetException e) {
            Log4a.printException(e);
        }
        return null;
    }


    public static void callMethod(String methodName, Class parametersClazz, Object parametersObject, Class clazzReference, Object clazzObject) {
        //String parameter
        Class[] parametersClass = new Class[1];
        parametersClass[0] = parametersClazz;

        //call the printItString method, pass a String param
        try {
            Method method = clazzReference.getDeclaredMethod(methodName, parametersClass);
            method.invoke(clazzObject, parametersObject);
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            Log4a.e("NoSuchMethodExce|SecurityExc", e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            Log4a.e("IllegalAccessException", e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            Log4a.e("IllegalArgumentException", e.getLocalizedMessage());
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            Log4a.e("InvocationTargetException", e.getLocalizedMessage());
        }

    }

    public static String getClassName(Class<?> cls) {
        Class<?> enclosingClass = cls.getEnclosingClass();
        if (enclosingClass != null) {
            return enclosingClass.getName();
        } else {
            return cls.getName();
        }
    }

    public static ArrayList<Object> findFieldsName(Class<?> cls, Object obj) {
        ArrayList<Object> list = new ArrayList<Object>();
        for (Field f : cls.getDeclaredFields()) {
            try {
                list.add(f.get(obj));
            } catch (IllegalAccessException | IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<Method> findGettersSetters(Class<?> c,
                                                       Boolean isGetterRequired) {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetterRequired) {
                if (isGetter(method))
                    list.add(method);
            } else {
                if (isSetter(method))
                    list.add(method);
            }
        }
        return list;
    }

    public static boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*")
                    && !method.getReturnType().equals(void.class))
                return true;
            if (method.getName().matches("^is[A-Z].*")
                    && method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }

    public static boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(void.class)
                && method.getParameterTypes().length == 1
                && method.getName().matches("^set[A-Z].*");
    }
}
