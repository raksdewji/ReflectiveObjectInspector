package ReflectiveObjectInspectorTests;

import ReflectiveObjectInspector.Inspector;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.assertNotNull;

public class InspectorTest {

    @Test
    void inspect(){
        Inspector inspector = new Inspector();
        Class inspectorClass = inspector.getClass();
        assertNotNull(inspectorClass);
        assertNotNull(inspector.getClass());
    }
}
