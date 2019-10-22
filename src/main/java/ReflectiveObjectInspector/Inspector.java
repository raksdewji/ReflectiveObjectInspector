package ReflectiveObjectInspector;

import java.lang.reflect.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        print("Class: " + getClassName(c), depth);
        getSuperClassName(c, obj, recursive, depth+1);
        getInterfaceName(c, obj, recursive, depth+1);
        getConstructor(c, depth+1);
        getMethod(c, depth+1);
        getFieldInfo(c, obj, recursive, depth+1);
    }

    private String getClassName(Class c) { return c.getName(); }

    private void getSuperClassName(Class child, Object obj, boolean recursive, int depth) {

        if (child.equals(Object.class)){
            return;
        }

        Class superClass = child.getSuperclass();
        int indent = depth+1;

        if (superClass != null) {
            print("Super Class Name: " + superClass.getName(), depth);
            inspectClass(superClass, obj, recursive, indent);
        }
        else {
            print("No super class found", indent);
        }
    }

    private void getInterfaceName(Class c, Object obj, boolean recursive, int depth) {
        Class[] classInterfaces = c.getInterfaces();
        int indent = depth + 1;
        if (classInterfaces.length > 0) {
            for (Class i : classInterfaces) {
                print("Class Implements Interface: " + i.getName(), depth);
                inspectClass(i, obj, recursive, indent);
            }
        }
    }

    private void getConstructor(Class c, int depth) {
        Constructor[] classConstructors = c.getConstructors();
        int indent = depth+1;
        if (classConstructors.length > 0) {
            for (Constructor con : classConstructors) {
                print("Constructor Name: " + con.getName(), depth);
                print("Constructor Modifiers: " + Modifier.toString(con.getModifiers()), indent);

                Class[] paramTypes = con.getParameterTypes();
                for (Class paramType : paramTypes)
                    print("Constructor Parameter Types: " + paramType.getName(), indent);
            }
        }
    }

    private void getMethod(Class c, int depth) {
        Method[] methods = c.getDeclaredMethods();
        int indent = depth +1;

        if (methods.length > 0) {
            for (Method m : methods) {
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
        }
    }

    private void getFieldInfo(Class c, Object obj, boolean recursive, int depth) {
        Field[] fields = c.getDeclaredFields();
        int indent = depth + 1;

        if (fields.length > 0) {
            for (Field f : fields) {
                print("Field Name: " + f.getName(), depth);
                Class type = f.getType();
                print("Field Type: " + type.getSimpleName(), indent);
                String modifiers = Modifier.toString(f.getModifiers());
                print("Field Modifiers: " + modifiers, indent);

                try {
                    Object vObj = f.get(obj);

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
                } catch (IllegalAccessException e) {
                    System.out.println("Field not accessible");
                }
            }
        }
    }

    private void getArrayInfo(Class c, Object obj, boolean recursive, int depth) {
        Class cType = c.getComponentType();
        int indent = depth + 1;
        int aLength = Array.getLength(obj);

        print("Component Type: " + cType.getSimpleName(), indent);
        print("Array Length: " + aLength, indent);

        for (int i = 0; i < aLength; i++) {
            Object aObject = Array.get(obj, i);

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
    }

    private void print(String output, int depth) {
        for (int i = 0; i < depth; i++)
            System.out.print("  ");

        System.out.println(output);
    }


}
