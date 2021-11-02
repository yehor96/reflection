package com.reflection;

import com.reflection.testhandler.annotations.DisplayName;
import com.reflection.testhandler.annotations.UnitTest;
import com.reflection.testobjects.Object1;
import com.reflection.testobjects.Object2;
import com.reflection.testobjects.Object3;
import com.reflection.testobjects.Object4;
import com.reflection.testobjects.Object5;
import com.reflection.testobjects.Object6;

import java.io.Serializable;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionUtilsTest {

    @UnitTest
    @DisplayName("getInstanceOf -> one public constructor")
    public void testGetInstanceOfWithOnePublicConstructor() throws Exception {
        Class testClass = Object1.class;

        Object instance = ReflectionUtils.getInstanceOf(testClass);

        assertNotNull(instance);
        assertSame(instance.getClass(), testClass);
        assertEquals("created", ((Object1) instance).field);
    }

    @UnitTest
    @DisplayName("getInstanceOf -> two public constructors")
    public void testGetInstanceOfWithTwoPublicConstructors() throws Exception {
        Class testClass = Object2.class;

        Object instance = ReflectionUtils.getInstanceOf(testClass);

        assertNotNull(instance);
        assertSame(instance.getClass(), testClass);
        assertEquals("created", ((Object2) instance).field);
    }

    @UnitTest
    @DisplayName("getInstanceOf -> one public non-default constructors")
    public void testGetInstanceOfWithOneNonDefaultConstructor() throws Exception {
        Class testClass = Object3.class;

        Object instance = ReflectionUtils.getInstanceOf(testClass);

        assertNotNull(instance);
        assertSame(instance.getClass(), testClass);
        assertEquals("created", ((Object3) instance).field);
    }

    @UnitTest
    @DisplayName("callMethodsWithoutParamsOf -> methods w/ and w/o params")
    public void testCallMethodsWithoutParamsOfObjectWithPublicPrivateMethods() throws Exception {
        Object4 testObject = new Object4();

        ReflectionUtils.callMethodsWithoutParamsOf(testObject);

        assertEquals("called", testObject.field1);
        assertEquals("called", testObject.field2);
        assertEquals("not called", testObject.field3);
        assertEquals("not called", testObject.field4);
        assertEquals("called", testObject.field5);
        assertEquals("not called", testObject.field6);
    }

    @UnitTest
    @DisplayName("printFinalMethods (getFinalMethods) returns all final methods")
    public void testGetFinalMethods() {
        var returnedMethods = ReflectionUtils.getFinalMethods(new Object6())
                .stream()
                .map(Method::toString)
                .collect(Collectors.toList());

        assertTrue(returnedMethods.contains("public final void com.reflection.testobjects.Object6.method1()"));
        assertTrue(returnedMethods.contains("protected final void com.reflection.testobjects.Object6.method3()"));
        assertTrue(returnedMethods.contains("private final void com.reflection.testobjects.Object6.method5()"));
        assertEquals(3, returnedMethods.size());
    }

    @UnitTest
    @DisplayName("printNonPublicMethods (getNonPublicMethods) returns all non public methods")
    public void testGetNonPublicMethods() {
        var returnedMethods = ReflectionUtils.getNonPublicMethods(Object6.class)
                .stream()
                .map(Method::toString)
                .collect(Collectors.toList());

        assertTrue(returnedMethods.contains("private void com.reflection.testobjects.Object6.method6()"));
        assertTrue(returnedMethods.contains("protected void com.reflection.testobjects.Object6.method4()"));
        assertTrue(returnedMethods.contains("private final void com.reflection.testobjects.Object6.method5()"));
        assertTrue(returnedMethods.contains("protected final void com.reflection.testobjects.Object6.method3()"));
        assertEquals(4, returnedMethods.size());
    }

    @UnitTest
    @DisplayName("changePrivateFieldsToDefaultValues() changes all private fields to default state")
    public void testChangePrivateFieldsToDefaultValues() throws IllegalAccessException {
        Object5 testObject = new Object5();
        String expectedValue = "Object5{visibleText='unchanged', text='null', bol=false, integer=0, aLong=0, aShort=0, aByte=0, aFloat=0.0, aDouble=0.0, aChar=\u0000, object5=null}";

        ReflectionUtils.changePrivateFieldsToDefaultValues(testObject);

        assertEquals(expectedValue, testObject.toString());
    }

    @UnitTest
    @DisplayName("printAncestorsAndInterfaces (getAncestors) method returns all ancestors")
    public void testGetAncestors() {
        var returnedAncestors = ReflectionUtils.getAncestors(String.class);

        assertTrue(returnedAncestors.contains(Object.class));
        assertEquals(1, returnedAncestors.size());
    }

    @UnitTest
    @DisplayName("printAncestorsAndInterfaces (getInterfaces) method returns all interfaces")
    public void testGetInterfaces() {
        var returnedInterfaces = ReflectionUtils.getInterfaces(String.class);

        assertTrue(returnedInterfaces.contains(Serializable.class));
        assertTrue(returnedInterfaces.contains(Comparable.class));
        assertTrue(returnedInterfaces.contains(CharSequence.class));
        assertTrue(returnedInterfaces.contains(Constable.class));
        assertTrue(returnedInterfaces.contains(ConstantDesc.class));
        assertEquals(5, returnedInterfaces.size());
    }
}
