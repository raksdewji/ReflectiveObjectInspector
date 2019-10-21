package ReflectiveObjectInspector;

import java.lang.reflect.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        print(getClassName(c), depth);
        getSuperClassName(c, obj, recursive, depth+1);
        getInterfaceName(c, obj, recursive, depth+1);
        getConstructor(c, depth+1);
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

        if (classInterfaces.length > 0){
            for (Class i : classInterfaces) {
                print("Class Implements Interface: " + i.getName(), depth);
                inspectClass(c, obj, recursive, indent);
            }
        }
    }

    private void getConstructor(Class c, int depth) {
        Constructor[] classConstructors = c.getConstructors();
        int indent = depth+1;
        if (classConstructors.length > 0) {
            for (Constructor con : classConstructors) {
                print("Constructor Name: " + con.getName(), depth);

                Class[] paramTypes = con.getParameterTypes();
                for (Class paramType : paramTypes)
                    print("Parameter Types: " + paramType.getName(), indent);

                print("Modifiers: " + Modifier.toString(con.getModifiers()), indent);
            }
        }
    }

    private void print(String output, int depth) {
        for (int i = 0; i < depth; i++)
            System.out.print("\t");

        System.out.println(output);
    }


}
