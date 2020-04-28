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


    public static void main(String[] args) throws Exception {
        InputStream webXmlIns = Thread.currentThread().getContextClassLoader().getResourceAsStream(webXmlSrc);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        WebHandler webHandler = new WebHandler();
        parser.parse(webXmlIns, webHandler);

        WebContext webContext = new WebContext(webHandler.getEntities(), webHandler.getMappings());

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
            revice();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("ServerSocket exception");
        }
    }

    public void revice(){
        try {
            Socket request = serverSocket.accept();
            System.out.println("一个链接建立---》");
            InputStream ins = request.getInputStream();
            byte[] car = new byte[1024*1024];
            int len = ins.read(car);
            String strContent = new String(car, 0, len);
            System.out.println(strContent);
//            ins.close();
            send(request);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("客户端连接异常");
        }
    }


    public void send(Socket request){
        try {
            OutputStream out = request.getOutputStream();
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(out));
            StringBuilder res = new StringBuilder();
            StringBuilder body = new StringBuilder();
            String blank = " ";
            String CRLF = "\r\n";

            body.append("<html>");
            body.append("<head><title>首页</title></head>");
            body.append("<body><style>h1{text-align: center;}</style> <h1>Hello world</h1> </body>");

            res.append("HTTP/1.1 ").append(blank);
            res.append(200).append(blank);
            res.append("OK").append(CRLF);
            res.append("Content-Type: text/html;").append(CRLF);
            res.append("Date:").append(new Date()).append(CRLF);
            res.append("Content-Length:").append(body.length()).append(CRLF);
            res.append(CRLF);

            res.append(body.toString());

            write.write(res.toString());

            write.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
