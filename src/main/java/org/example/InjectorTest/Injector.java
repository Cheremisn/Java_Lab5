package org.example.InjectorTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * A class that performs dependency injection on any object that contains fields marked with @AutoInjectable
 */
public class Injector {
    /**
     * a method that takes an object of any class as a parameter and,
     * using reflection mechanisms, searches for fields marked with
     * this annotation and initializes these fields with class instances
     * @param object that have to be DI-ed
     * @return a reference to DI-ed object
     * @param <T> a type of returning reference
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public <T extends Object> T inject(Object object) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class clasz = object.getClass();
        Field[] fields = clasz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(AutoInjectable.class);
            if (annotation == null) {
                continue;
            }
            field.setAccessible(true);
            Class valueClass = null;
            try {
                valueClass = Class.forName(propertiesHandler(String.valueOf((field.getType().toString().split(" ")[1]))));
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            Object vc = null;
            vc = valueClass.getDeclaredConstructor().newInstance();
            field.set(object, vc);

        }
        return (T) object;
    }

    /**
     *a method that searches for an implementation in a property file
     * @param propertyKey is a String value that represent a name of interface
     * @return a String value that represent a name of searching implementation
     */
    public String propertiesHandler(String propertyKey) {
        FileInputStream fis;
        Properties property = new Properties();
        String propertyValue = null;

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

//            System.out.println(propertyKey);
            propertyValue = property.getProperty(propertyKey);
//            System.out.println(propertyValue);

        } catch (IOException e) {
            System.err.println("No property file!");
        }
        return propertyValue;
    }
}