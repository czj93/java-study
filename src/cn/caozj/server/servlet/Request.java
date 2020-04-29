package cn.caozj.server.servlet;

/**
 * 解析 http 请求
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public class Request {
    static String CRLF = "\r\n";
    static String blank = " ";
    private String requestInfo;
    private String path;
    private String fullPath;
    private String queryStr;
    private HashMap<String, String> queryMap;
    private HashMap<String, String> headerMap;
    private String method;
    private String body;
    private HashMap<String, String> parameterMap;
    public Request(InputStream ins) throws IOException {
        queryMap = new HashMap<>();
        headerMap = new HashMap<>();
        parameterMap = new HashMap<>();

        byte[] car = new byte[1024*1024];
        int len = ins.read(car);
        requestInfo = new String(car, 0, len);
        System.out.println(requestInfo);

        parse();
    }

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    public void parse(){
        int startIndex = 0,
            endIndex = requestInfo.indexOf("/");
        // 解析请求方式
        method = requestInfo.substring(startIndex, endIndex).trim().toLowerCase();

        startIndex += endIndex;
        endIndex = requestInfo.indexOf(blank, startIndex);

        // 获取请求路径
        fullPath = requestInfo.substring(startIndex, endIndex);
        // 解析地址 获取 query
        int queryStartIndex = fullPath.indexOf("?");
        if(queryStartIndex != -1){
            path = fullPath.substring(0, queryStartIndex);
            queryStr = fullPath.substring(queryStartIndex);
            if(queryStr.length() > 1){
                parseQuery(queryStr.substring(1), queryMap);
            }
        }

        // 解析header
        startIndex = requestInfo.indexOf(CRLF);
        startIndex += CRLF.length();
        endIndex = requestInfo.lastIndexOf(CRLF);

        String[] headers = requestInfo.substring(startIndex, endIndex).split(CRLF);

        for (String h:headers){
            System.out.println(h);
            String[] kv = h.split(":");
            kv = Arrays.copyOf(kv, 2);
            String key = kv[0].toLowerCase();
            String value = kv[1];
            headerMap.put(key, value);
            if(key.equals("cookies")){
                // 解析cookies
            }
        }
        // post请求  获取body
        if(method.equals("post")){
            body = requestInfo.substring(endIndex+CRLF.length());

            // 数据编码方式  form-data application/x-www-form-urlencoded json xml ....
            // application/x-www-form-urlencoded
            if(body.trim().length() > 0){
                String contentType = headerMap.get("content-type");
                if(contentType.contains("application/x-www-form-urlencoded")){
                    parseQuery(body, parameterMap);
                }else if(contentType.contains("json")){
                    // 解析json
                }else if(contentType.contains("form-data")){

                }
            }

        }

    }


    public void parseQuery(String str, HashMap<String, String> map){
        String[] params = str.split("&");
        for(String param:params){
            if(!param.equals("")){
                String[] kv= param.split("=");
                kv = Arrays.copyOf(kv, 2);
                String key = kv[0];
                String value = kv[1];
                map.put(key, value);
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getQuery(String key) {
        return queryMap.get(key);
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getMethod() {
        return method;
    }

    public String getParameters(String key) {
        return parameterMap.get(key);
    }


}
