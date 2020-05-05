package cn.caozj.server.web;

import cn.caozj.server.servlet.Request;
import cn.caozj.server.servlet.Response;
import cn.caozj.server.servlet.Servlet;
import cn.caozj.server.servlet.WebServlet;

@WebServlet("/test-servlet")
public class testServlet implements Servlet {

    public void service(Request request, Response response){
        response.print("test annotation");
    }
}
