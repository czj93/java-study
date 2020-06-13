package cn.caozj.sorm.utils;

import java.lang.reflect.Method;

public class ReflectUtils {

    public static Object invokeGet(Class clz, Object obj, String fieldName){
        String methodName = "get" + StringUtils.firstChar2UpperCase(fieldName);
        try {
            Method getPrikey = clz.getDeclaredMethod(methodName);
            return getPrikey.invoke(obj, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public  static void invokeSet( Object obj, String fieldName, Object value){
        Class clz = obj.getClass();
        String methodName = "set" + StringUtils.firstChar2UpperCase(fieldName);
        try {
            Method setMethod = clz.getDeclaredMethod(methodName, value.getClass());
            if(value != null){
                setMethod.invoke(obj, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
