package cn.caozj.server.servlet;

import java.net.Socket;

public class Dispatcher implements Runnable  {
    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client){
        this.client = client;
        try {
            request = new Request(client);
            response = new Response(client);
        }catch (Exception e){
            e.printStackTrace();
            reslease();
        }
    }


    @Override
    public void run() {
        try {
            Servlet servlet = WebApp.getServletFormUrl(request.getPath());
            if(servlet != null){
                servlet.service(request, response);
                response.send(200);
            }else{
                response.send(404);
            }
        }catch (Exception e){
            response.send(500);
            e.printStackTrace();
        }

        // 释放资源  短连接
        // 不释放则是长链接
        // ？？？ 这里不释放的话， 下一个链接无法建立， 那么长链接又是怎么做的呢？？？
        reslease();
    }

    public void reslease(){
        try {
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
