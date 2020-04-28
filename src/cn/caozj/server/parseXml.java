package cn.caozj.server;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;

// 解析xml 文件
// 1. 读取 xml 文件
public class parseXml {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // 获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // 获取解析器
        SAXParser parser = factory.newSAXParser();
        // 编写处理器
        PersonXmlHandle handler = new PersonXmlHandle();
        InputStream personsXml = Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/caozj/server/demo.xml");
        parser.parse(personsXml, handler);
    }
}
