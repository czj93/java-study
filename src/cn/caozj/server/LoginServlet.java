package cn.caozj.server;

import cn.caozj.server.servlet.Servlet;

public class LoginServlet implements Servlet {
    @Override
    public void service() {
        System.out.println("login service");
    }
}
