package cn.caozj.server.servlet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class WebHandler extends DefaultHandler {
    private List<Entity> entities;
    private List<Mapping> mappings;

    private String tag;
    private Entity entity;
    private Mapping mapping;
    private Boolean isServlet = true;

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    @Override
    public void startDocument() throws SAXException {
        entities = new ArrayList<Entity>();
        mappings = new ArrayList<Mapping>();
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(!qName.trim().equals("")){
            tag = qName;

            if(tag.equals("servlet")){
                entity = new Entity();
                isServlet = true;
            }else if(tag.equals("servlet-mapping")){
                mapping = new Mapping();
                isServlet = false;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("servlet")){
            entities.add(entity);
        }else if(qName.equals("servlet-mapping")){
            mappings.add(mapping);
        }
        tag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(tag != null){
            String content = new String(ch, start, length);

            if(!content.trim().equals("")){
                if(isServlet){
                    if(tag.equals("servlet-name")){
                        entity.setName(content);
                    }else if(tag.equals("servlet-class")){
                        entity.setClz(content);
                    }
                }else{
                    if(tag.equals("servlet-name")){
                        mapping.setName(content);
                    }else if(tag.equals("url-pattern")){
                        mapping.addPattern(content);
                    }
                }
            }
        }
    }
}
