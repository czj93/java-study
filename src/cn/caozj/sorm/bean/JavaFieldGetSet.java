package cn.caozj.sorm.bean;

public class JavaFieldGetSet {

    public JavaFieldGetSet(){}
    public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
        this.fieldInfo = fieldInfo;
        this.getInfo = getInfo;
        this.setInfo = setInfo;
    }

    /**
     * 属性信息 如： public int name;
     */
    private String fieldInfo;

    /**
     * get方法 public String getName(){ return this.name; }
     */
    private String getInfo;

    /**
     * set 方法 public void setName(String name){ this.name = name; }
     */
    private  String setInfo;

    public String getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getGetInfo() {
        return getInfo;
    }

    public void setGetInfo(String getInfo) {
        this.getInfo = getInfo;
    }

    public String getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }
}
