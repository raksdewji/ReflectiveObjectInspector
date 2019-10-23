package ReflectiveObjectInspector;

import java.lang.reflect.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    public void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        print("Class: " + getClassName(c), depth);
        getSuperClassName(c, obj, recursive, depth+1);
        getInterfaceName(c, obj, recursive, depth+1);
        getConstructor(c, depth+1);
        getMethod(c, depth+1);
        getFieldInfo(c, obj, recursive, depth+1);
    }

    public String getClassName(Class c) { return c.getName(); }

    public void getSuperClassName(Class child, Object obj, boolean recursive, int depth) {
        Class superClass = child.getSuperclass();
        int indent = depth+1;

        if (child.equals(Object.class)){
            return;
        }

        if (superClass != null) {
            print("Super Class Name: " + superClass.getName(), depth);
            inspectClass(superClass, obj, recursive, indent);
        }
        else {
            print("No super class found", indent);
        }
    }

    public void getInterfaceName(Class c, Object obj, boolean recursive, int depth) {
        Class[] classInterfaces = c.getInterfaces();
        int indent = depth + 1;
        if (classInterfaces.length > 0) {
            for (Class i : classInterfaces) {
                print("Class Implements Interface: " + i.getName(), depth);
                inspectClass(i, obj, recursive, indent);
            }
        }
    }

    public void getConstructor(Class c, int depth) {
        Constructor[] classConstructors = c.getConstructors();
        int indent = depth+1;
        if (classConstructors.length > 0) {
            for (Constructor con : classConstructors) {
                printConstructorInfo(depth, indent, con);
            }
        }
    }

    public void printConstructorInfo(int depth, int indent, Constructor con) {
        print("Constructor Name: " + con.getName(), depth);
        print("Constructor Modifiers: " + Modifier.toString(con.getModifiers()), indent);

        Class[] paramTypes = con.getParameterTypes();
        for (Class paramType : paramTypes)
            print("Constructor Parameter Types: " + paramType.getName(), indent);
    }

    public void getMethod(Class c, int depth) {
        Method[] methods = c.getDeclaredMethods();
        int indent = depth +1;

        if (methods.length > 0) {
            for (Method m : methods) {
                printMethodInfo(depth, indent, m);
            }
        }
    }

    public void printMethodInfo(int depth, int indent, Method m) {
        print("Method: " + m.getName(), depth);
        print("Method Return Type: " + m.getReturnType(), indent);
        String modifier = Modifier.toString(m.getModifiers());
        print("Method Modifiers: " + modifier, indent);

        Class[] exceptions = m.getExceptionTypes();
        if (exceptions.length > 0) {
            for (Class e : exceptions) {
                print("Method Exceptions: " + e.getName(), indent);
            }
        }

        Class[] parameters = m.getParameterTypes();
        if (parameters.length > 0) {
            for (Class p : parameters) {
                print("Method Parameter Types: " + p.getName(), indent);
            }
        }
    }

    public void getFieldInfo(Class c, Object obj, boolean recursive, int depth) {
        Field[] fields = c.getDeclaredFields();
        int indent = depth + 1;

        if (fields.length > 0) {
            for (Field f : fields) {
                printFieldInfo(obj, recursive, depth, indent, f);
            }
        }
    }

    public void printFieldInfo(Object obj, boolean recursive, int depth, int indent, Field f) {
        f.setAccessible(true);
        print("Field Name: " + f.getName(), depth);
        Class type = f.getType();
        print("Field Type: " + type.getSimpleName(), indent);
        String modifiers = Modifier.toString(f.getModifiers());
        print("Field Modifiers: " + modifiers, indent);

        try {
            Object vObj = f.get(obj);
            checkObject(obj, recursive, indent, f, type, vObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void checkObject(Object obj, boolean recursive, int indent, Field f, Class type, Object vObj) {
        if (type.isArray()) {
            getArrayInfo(f.getType(), vObj, recursive, indent);
        }
        else if (type.isPrimitive()) {
            print("Value: " + vObj.toString(), indent);
        }
        else if (vObj == null) {
            print("Value: null", indent);
        }
        else {
            if (recursive) {
                inspectClass(type, vObj, true, indent);
            } else {
                print("Reference Value: " + vObj.getClass().getName()
                        + "@" + obj.hashCode(), indent);
            }
        }
    }

    public void getArrayInfo(Class c, Object obj, boolean recursive, int depth) {
        Class cType = c.getComponentType();
        int indent = depth + 1;
        int aLength = Array.getLength(obj);

        print("Component Type: " + cType.getSimpleName(), indent);
        print("Array Length: " + aLength, indent);

        for (int i = 0; i < aLength; i++) {
            Object aObject = Array.get(obj, i);
            checkArrayObject(recursive, cType, indent, aObject);
        }
    }

    public void checkArrayObject(boolean recursive, Class cType, int indent, Object aObject) {
        if (cType.isArray()) {
            getArrayInfo(aObject.getClass(), aObject, recursive, indent+1);
        }
        else if (cType.isPrimitive()) {
            print(aObject.getClass().getName(), indent+1);
        }
        else if (aObject == null) {
            print("null",indent+1);
        }
        else {
            if (recursive) {
                getArrayInfo(aObject.getClass(), aObject, recursive, indent+1);
            }
            else {
                print("Value: " + aObject.getClass().getName() + "@" + aObject.getClass().hashCode(), indent+1);
            }
        }
    }

    private void print(String output, int depth) {
        for (int i = 0; i < depth; i++)
            System.out.print("  ");
        System.out.println(output);
    }

}
