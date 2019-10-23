package ReflectiveObjectInspectorTests;

import ReflectiveObjectInspector.ClassB;
import ReflectiveObjectInspector.Inspector;
import ReflectiveObjectInspector.ClassA;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class InspectorTest {
    private static final  ByteArrayOutputStream output = new ByteArrayOutputStream();
    private ClassA a = new ClassA();

    @Before
    public void setupOutput() {
        System.setOut(new PrintStream(output));
    }

    @After
    public void resetOutput() {
        output.reset();
    }

    @Test
    public void testClassName(){
        new Inspector().getClassName(a.getClass(), 0);
        assert(output.toString().contains("ReflectiveObjectInspector.ClassA"));
    }

    @Test
    public void testSuperClass(){
        new Inspector().getSuperClassName(a.getClass(), a, false, 0);
        assert(output.toString().contains("Super Class Name: java.lang.Object"));
    }

    @Test
    public void testSuperClassRecursive(){
        new Inspector().getSuperClassName(a.getClass(), a, true, 0);
        assert(output.toString().contains("Super Class Name: java.lang.Object"));
    }

    @Test
    public void testSuperClassNull() {
        new Inspector().getSuperClassName(Runnable.class, null, false, 0);
        assert(output.toString().contains("No super class found"));
    }

    @Test
    public void testSuperClassNullRecursive() {
        new Inspector().getSuperClassName(Runnable.class, null, true, 0);
        assert(output.toString().contains("No super class found"));
    }

    @Test
    public void testObjectArray() {
        new Inspector().inspect(new ClassB[12], false);
        assert(output.toString().contains("Array Length: 12"));
        assert(!output.toString().contains("Super Class Name: java.lang.Object"));
    }

    @Test
    public void testConstructor() {
        new Inspector().getConstructor(String.class, 0);
        assert(output.toString().contains("Constructor Name: java.lang.String"));
        assert(output.toString().contains("Constructor Modifiers: public"));
        assert(output.toString().contains("Constructor Parameter Types: int"));
    }

    @Test
    public void testMethods() {
        new Inspector().getMethod(a.getClass(), 0);
        assert(output.toString().contains("Method: run"));
        assert(output.toString().contains("Method Return Type: void"));
        assert(output.toString().contains("Method Modifiers: public"));

        assert(output.toString().contains("Method: toString"));
        assert(output.toString().contains("Method Return Type: class java.lang.String"));

        assert(output.toString().contains("Method: setVal"));
        assert(output.toString().contains("Method Exceptions: java.lang.Exception"));
        assert(output.toString().contains("Method Modifiers: public"));
        assert(output.toString().contains("Method Parameter Types: int"));
    }

    @Test
    public void testMethodsRecursive() {
        new Inspector().getConstructor(String.class, 0);
        assert(output.toString().contains("Constructor Name: java.lang.String"));
        assert(output.toString().contains("Constructor Modifiers: public"));
        assert(output.toString().contains("Constructor Parameter Types: int"));
    }

    @Test
    public void testFields() {
        new Inspector().getFieldInfo(a.getClass(), a, false, 0);

        assert(output.toString().contains("Field Name: val"));
        assert(output.toString().contains("Field Type: int"));
        assert(output.toString().contains("Field Modifiers: private"));
        assert(output.toString().contains("Value: 3"));

        assert(output.toString().contains("Field Name: val2"));
        assert(output.toString().contains("Field Type: double"));
        assert(output.toString().contains("Field Modifiers: private"));
        assert(output.toString().contains("Value: 0.2"));

        assert(output.toString().contains("Field Name: val3"));
        assert(output.toString().contains("Field Type: boolean"));
        assert(output.toString().contains("Field Modifiers: private"));
        assert(output.toString().contains("Value: true"));
    }

    @Test
    public void testArray() {
        new Inspector().inspect("array", false);
        assert(output.toString().contains("Component Type: char"));
        assert(output.toString().contains("Array Length: 5"));
        assert(output.toString().contains("java.lang.Character"));
    }



}
