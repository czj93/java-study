package cn.caozj.sorm.bean;

import java.util.List;
import java.util.Map;

public class TableInfo {

    /**
     * 表名
     */
    private String tname;

    /**
     * 所有的字段信息
     */
    private Map<String, ColumnInfo> colums;

    /**
     * 主键字段
     */
    private ColumnInfo onlyPriKey;

    private List<ColumnInfo> priKeys;

    public TableInfo(){};

    public TableInfo(String tname, Map<String, ColumnInfo> colums, ColumnInfo onlyPriKey) {
        this.tname = tname;
        this.colums = colums;
        this.onlyPriKey = onlyPriKey;
    }

    public TableInfo(String tname, List<ColumnInfo> priKeys, Map<String, ColumnInfo> colums) {
        this.tname = tname;
        this.colums = colums;
        this.priKeys = priKeys;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Map<String, ColumnInfo> getColums() {
        return colums;
    }

    public void setColums(Map<String, ColumnInfo> colums) {
        this.colums = colums;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }

    public List<ColumnInfo> getPriKeys() {
        return priKeys;
    }

    public void setPriKeys(List<ColumnInfo> priKeys) {
        this.priKeys = priKeys;
    }
}
