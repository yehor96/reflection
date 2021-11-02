package com.reflection.testobjects;

public class Object4 {
    public String field1 = "not called";
    public String field2 = "not called";
    public String field3 = "not called";
    public String field4 = "not called";
    public String field5 = "not called";
    public String field6 = "not called";

    public void method1() {
        field1 = "called";
    }

    private void method2() {
        field2 = "called";
    }

    public void method3(String text) {
        field3 = "called";
    }

    private void method4(String text) {
        field4 = "called";
    }

    protected void method5() {
        field5 = "called";
    }

    protected void method6(String text) {
        field6 = "called";
    }
}
