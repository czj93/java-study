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

}
