package cn.caozj.server.servlet;

/**
 * 解析 http 请求
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Request {
    static String CRLF = "\r\n";
    static String blank = " ";
    String text;
    String path;
    String fullPath;
    HashMap<String, String> query;
    String method;
    public Request(String data) {
        text = data;
        parse();
    }

    public void parse(){
        ArrayList<String> list = new ArrayList<String>();

    }
}
