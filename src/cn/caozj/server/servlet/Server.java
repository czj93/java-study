package cn.caozj.server.servlet;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

// 1. 读取 web.xml 里的配置
public class Server {
    private int port = 8888;
    private ServerSocket serverSocket;
    static String webXmlSrc = "cn/caozj/server/servlet/web.xml";
    private Boolean isRunning = true;
    static WebContext webContext;


    public static void main(String[] args) throws Exception {
        InputStream webXmlIns = Thread.currentThread().getContextClassLoader().getResourceAsStream(webXmlSrc);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        WebHandler webHandler = new WebHandler();
        parser.parse(webXmlIns, webHandler);

        webContext = new WebContext(webHandler.getEntities(), webHandler.getMappings());

        Server server = new Server();
        server.start();

//        String className = webContext.getClz("/register");
//        Class clz = Class.forName(className);
//
//        Servlet servlet = (Servlet) clz.getConstructor().newInstance();
//        servlet.service();
    }


    public void start(){
        try {
            serverSocket = new ServerSocket(port);
            receive();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("ServerSocket exception");
        }
    }

    public void receive(){
        try {
            while (isRunning){
                Socket client = serverSocket.accept();
                WebServer ws = new WebServer(client, webContext);
                new Thread(ws).start();
            }

//            Socket client = serverSocket.accept();
//            System.out.println("一个链接建立---》");
//            Request request = new Request(client);
//            Response response = new Response(client);
//            String name = request.getQuery("name");
//            response.print(name + " hello world");
//            response.send(200);
        }catch (Exception e){
            isRunning = false;
            e.printStackTrace();
            System.out.println("客户端连接异常");
        }
    }

}
