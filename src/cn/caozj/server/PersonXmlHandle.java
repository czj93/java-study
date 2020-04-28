package cn.caozj.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class PersonXmlHandle extends DefaultHandler {

    public void characters(char[] ch, int start,  int length){
        String content = new String(ch, start, length);
        System.out.println( "内容：" + content);
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("开始解析");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("结束解析");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // qName标签
        System.out.println("qName:" + qName + "解析开始");
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("qName:" + qName + " 解析结束");
    }
}
