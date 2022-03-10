package org.utils;



import org.constants.FrameworkConstants;
import org.enums.PropertiesType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class PropertyUtils {

    private PropertyUtils(){}

    //read property file content ,then use hashmap to store it
    //read content once and use java collection to store it

    private static Properties properties= new Properties();
    private static Map<String,String> configValues = new HashMap<>();

    //before the whole test execution starts
    static{
        try(FileInputStream inStream = new FileInputStream(FrameworkConstants.getPropertyFilePath())) { //closeable
            properties.load(inStream);
        }
        catch (IOException exp) {
            exp.printStackTrace();
            System.exit(0);
        }
        properties.forEach((key, value) -> configValues.put(String.valueOf(key), String.valueOf(value)));
    }

    public static String getValue(PropertiesType key){
        return configValues.get(key.name().toLowerCase());
    }
}
