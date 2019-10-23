package ReflectiveObjectInspectorTests;

import ReflectiveObjectInspector.Inspector;
import ReflectiveObjectInspector.ClassA;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class InspectorTest {
    private static final  ByteArrayOutputStream output = new ByteArrayOutputStream();

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
        ClassA a = new ClassA();
        String inspector = new Inspector().getClassName(a.getClass());
        assertEquals("ReflectiveObjectInspector.ClassA", inspector);
    }

    @Test
    public void testSuperClass(){
        ClassA a = new ClassA();
        new Inspector().getSuperClassName(a.getClass(), a, false, 0);
        assert(output.toString().contains("Super Class Name: java.lang.Object"));
    }

    @Test
    public void testSuperClassRecursive(){
        ClassA a = new ClassA();
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
}
