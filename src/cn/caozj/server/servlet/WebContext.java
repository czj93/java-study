package cn.caozj.server.servlet;

import java.util.HashMap;
import java.util.List;

public class WebContext {
    private List<Entity> entities = null;
    private List<Mapping> mappings = null;

    private HashMap<String, String> entityMap = new HashMap<>();
    private  HashMap<String, String> mappingMap = new HashMap<>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        for(Entity entity:entities){
            entityMap.put(entity.getName(), entity.getClz());
        }

        for (Mapping mapping:mappings){
            for (String parrent: mapping.getPatterns()){
                mappingMap.put(parrent, mapping.getName());
            }
        }
    }

    // 通过 url 获取对应的 Class
    public String getClz(String pattern){
        String name = mappingMap.get(pattern);
        return entityMap.get(name);
    }

    public void addServlet(String packageName, String url){
        System.out.println(packageName + ":" + url);
//        String[] names = packageName.split(".");   // split 分割失败 ？？？

        mappingMap.put(url, packageName);
        entityMap.put(packageName, packageName);
    }
}
