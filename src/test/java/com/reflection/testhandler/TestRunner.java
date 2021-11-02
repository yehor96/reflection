package com.reflection.testhandler;

import com.reflection.ReflectionUtilsTest;

import static com.reflection.testhandler.TestProcessor.run;

public class TestRunner {
    public static void main(String[] args) {
        run(new ReflectionUtilsTest());
    }
}
