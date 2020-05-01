package cn.caozj.server.servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

// 1. 读取 web.xml 里的配置

public class WebApp {
    static WebContext webContext;
    static String webXmlSrc = "cn/caozj/server/servlet/web.xml";
    static {
        try {
            InputStream webXmlIns = Thread.currentThread().getContextClassLoader().getResourceAsStream(webXmlSrc);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            WebHandler webHandler = new WebHandler();
            parser.parse(webXmlIns, webHandler);
            webContext = new WebContext(webHandler.getEntities(), webHandler.getMappings());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static public Servlet getServletFormUrl(String url) {
        String className = webContext.getClz(url);
        try {
            Servlet servlet = (Servlet) Class.forName(className).getConstructor().newInstance();
            return servlet;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
