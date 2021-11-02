package com.reflection.testhandler;

import com.reflection.testhandler.annotations.DisplayName;
import com.reflection.testhandler.annotations.UnitTest;

import java.lang.reflect.Method;

public class TestProcessor {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void run(Object testClass) {
        Class clazz = testClass.getClass();
        for (Method method : clazz.getMethods()) {
            if (method.getAnnotation(UnitTest.class) != null) {
                StringBuilder result = new StringBuilder("Test [");
                DisplayName displayNameAnnotation = method.getAnnotation(DisplayName.class);
                if (displayNameAnnotation != null) {
                    result.append(displayNameAnnotation.value());
                } else {
                    result.append(method.getName());
                }
                result.append("]");

                try {
                    method.invoke(testClass);
                    result.append(" Passed");
                    display(result, true);
                } catch (Exception e) {
                    result.append(" Failed. ").append(e.getCause().getMessage());
                    display(result, false);
                }
            }
        }

    }

    public static void display(StringBuilder result, boolean passed) {
        if (passed) {
            System.out.println(ANSI_GREEN + result + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + result + ANSI_RESET);
        }
    }
}
