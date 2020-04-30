package cn.caozj.server.servlet;

import java.net.Socket;

public class WebServer implements Runnable  {
    Class clz;
    Socket client;
    Request request;
    Response response;

    public WebServer(Socket client, WebContext webContext)  throws Exception {
        this.client = client;
        request = new Request(client);
        response = new Response(client);
        System.out.println(request.getPath());
        String className = webContext.getClz(request.getPath());
        clz = Class.forName(className);

    }


    @Override
    public void run() {
        try {
            Servlet servlet = (Servlet) clz.getConstructor().newInstance();
            servlet.service(request, response);
        }catch (Exception e){
            e.printStackTrace();
            reslease();
        }
    }

    public void reslease(){
        try {
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
