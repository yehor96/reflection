package com.reflection.testobjects;

public class Object5 {

    public String visibleText;
    private String text;
    private boolean bol;
    private int integer;
    private long aLong ;
    private short aShort;
    private byte aByte;
    private float aFloat;
    private double aDouble;
    private char aChar;
    private Object object5;

    public Object5() {
        visibleText = "unchanged";
        text = "text";
        bol = true;
        integer = 5;
        aLong = 5L;
        aShort = 10;
        aByte = 8;
        aFloat = 3.3f;
        aDouble = 10.1;
        aChar = 'g';
        object5 = new Object();
    }

    @Override
    public String toString() {
        return "Object5{" +
                "visibleText='" + visibleText + '\'' +
                ", text='" + text + '\'' +
                ", bol=" + bol +
                ", integer=" + integer +
                ", aLong=" + aLong +
                ", aShort=" + aShort +
                ", aByte=" + aByte +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", aChar=" + aChar +
                ", object5=" + object5 +
                '}';
    }
}
