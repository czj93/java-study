package cn.caozj.server;

import cn.caozj.server.servlet.Request;
import cn.caozj.server.servlet.Response;
import cn.caozj.server.servlet.Servlet;

import java.io.IOException;

public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        response.print("Hello world");
    }
}
