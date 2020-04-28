package cn.caozj.server;

import cn.caozj.server.servlet.Servlet;

public class RegisterServlet implements Servlet {
    @Override
    public void service() {
        System.out.println("register service");
    }
}
