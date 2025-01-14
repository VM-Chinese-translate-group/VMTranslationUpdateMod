package top.vmctcn.vmtucore.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Under AGPL License
 *
 * @author CFPAOrg/I18nUpdateMod3 (xfl03)
 */
public class ReflectionHelper {
    private final Class<?> clazz;
    private Object instance;

    public ReflectionHelper(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ReflectionHelper(Object instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Instance cannot be null");
        }
        this.clazz = instance.getClass();
        this.instance = instance;
    }

    public static ReflectionHelper clazz(String className) throws ClassNotFoundException {
        return new ReflectionHelper(Class.forName(className));
    }

    public static ReflectionHelper clazz(Class<?> clazz) {
        return new ReflectionHelper(clazz);
    }

    public static ReflectionHelper clazz(Object instance) {
        return new ReflectionHelper(instance);
    }

    private ReflectionHelper getField(String field) throws Exception {
        Field field0 = clazz.getDeclaredField(field);
        field0.setAccessible(true);
        return new ReflectionHelper(field0.get(instance));
    }

    private ReflectionHelper invokeMethod(String method) throws Exception {
        Method method1 = clazz.getDeclaredMethod(method);
        method1.setAccessible(true);
        return new ReflectionHelper(method1.invoke(instance));
    }

    /**
     * Get field or invoke method
     *
     * @param fieldOrMethod fieldName or methodName()
     * @return result
     * @throws Exception
     */
    public ReflectionHelper get(String fieldOrMethod) throws Exception {
        if (fieldOrMethod.endsWith(")")) {
            return invokeMethod(fieldOrMethod.replace("()", ""));
        } else {
            return getField(fieldOrMethod);
        }
    }

    public Object get() {
        return instance;
    }
}
