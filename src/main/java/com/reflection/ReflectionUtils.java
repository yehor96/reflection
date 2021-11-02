package com.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReflectionUtils {

    // 1. Method accepts a class and returns its instance
    public static Object getInstanceOf(Class clazz) throws Exception {
        if (Objects.isNull(clazz)) {
            throw new IllegalArgumentException("Cannot create instance of null");
        }

        Constructor[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("Cannot create instance of class [" + clazz.getSimpleName() + "]");
        }

        for (var constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
        }
        for (var constructor : constructors) {
            if (constructor.getParameterCount() == 1) {
                Object obj = getInstanceOf(constructor.getParameterTypes()[0]);
                return constructor.newInstance(obj);
            }
        }
        throw new IllegalArgumentException("Cannot create instance of class [" + clazz.getSimpleName() + "]. Too many arguments");
    }

    // 2. Method accepts an object and calls all of its methods that do not contain parameters
    public static void callMethodsWithoutParamsOf(Object object) throws Exception {
        Class clazz = object.getClass();
        for (var method : clazz.getMethods()) {
            if (method.getParameterCount() == 0) {
                method.invoke(object);
            }
        }
    }

    // 3. Method accepts object and prints signatures of all its final methods
    public static void printFinalMethods(Object object) {
        List<Method> methods = getFinalMethods(object);
        for (Method method : methods) {
            System.out.println(method.toString());
        }
    }

    // 4. Method accepts Class and prints all non-public methods of the class
    public static void printNonPublicMethods(Class clazz) {
        List<Method> methods = getNonPublicMethods(clazz);
        for (Method method : methods) {
            System.out.println(method.toString());
        }
    }

    // 5.Method accepts a Class and prints all its ancestors and interfaces it implements
    public static void printAncestorsAndInterfaces(Class clazz) {
        for (Class ancestor : getAncestors(clazz)) {
            System.out.println(ancestor.getSimpleName());
        }
        for (Class anInterface : getInterfaces(clazz)) {
            System.out.println(anInterface.getSimpleName());
        }
    }

    //6. Method accepts an object and changes all its private fields to their default value
    public static void changePrivateFieldsToDefaultValues(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.toString().startsWith("private")) {
                field.setAccessible(true);
                switch (field.getType().getSimpleName()) {
                    case "int" -> field.set(object, 0);
                    case "byte" -> field.set(object, (byte) 0);
                    case "short" -> field.set(object, (short) 0);
                    case "long" -> field.set(object, 0L);
                    case "float" -> field.set(object, 0.0f);
                    case "double" -> field.set(object, 0.0d);
                    case "char" -> field.set(object, '\u0000');
                    case "boolean" -> field.set(object, false);
                    default -> field.set(object, null);
                }
            }
        }
    }

    static List<Class> getAncestors(Class clazz) {
        Class superClazz = clazz.getSuperclass();
        List<Class> list = new ArrayList<>();
        while (superClazz != null) {
            list.add(superClazz);
            superClazz = superClazz.getSuperclass();
        }
        return list;
    }

    static Class[] getInterfaces(Class clazz) {
        return clazz.getInterfaces();
    }

    static List<Method> getFinalMethods(Object object) {
        List<Method> list = new ArrayList<>();
        Class clazz = object.getClass();
        for (var method : clazz.getMethods()) {
            if (method.toString().contains("final")) {
                list.add(method);
            }
        }
        return list;
    }

    static List<Method> getNonPublicMethods(Class clazz) {
        List<Method> list = new ArrayList<>();
        for (var method : clazz.getDeclaredMethods()) {
            if (!method.toString().startsWith("public")) {
                list.add(method);
            }
        }
        return list;
    }
}
