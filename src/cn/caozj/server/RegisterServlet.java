package cn.caozj.server;

import cn.caozj.server.servlet.Request;
import cn.caozj.server.servlet.Response;
import cn.caozj.server.servlet.Servlet;

public class RegisterServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {

        response.print("register page");
        response.send(200);
    }
}
