package cn.caozj.server.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private int len;
    private StringBuilder header;           // 响应头 （状态行 header 空行）
    private StringBuilder content;

    private String blank = " ";
    private String CRLF = "\r\n";

    private BufferedWriter bw;
    public Response(){
        len = 0;
        header = new StringBuilder();
        content = new StringBuilder();
    }
    public Response(Socket client) throws IOException {
        this();   //重载 执行了无参构造方法
        bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    public  Response(OutputStream out){
        this();
        bw = new BufferedWriter(new OutputStreamWriter(out));
    }

    public Response print(String info){
        content.append(info);
        len += info.getBytes().length;
        return this;
    }
    public Response println(String info){
        content.append(info).append(CRLF);
        len += (CRLF + info).getBytes().length;
        return this;
    }

    public void createHeader(int code){
        header.append("HTTP/1.1").append(blank);
        header.append(code).append(blank);
        switch (code){
            case 200:
                header.append("OK").append(CRLF);
                break;
            case 404:
                header.append("NOT FOUND").append(CRLF);
                break;
            case 500:
                header.append("SERVER ERROR").append(CRLF);
                break;
        }
        header.append("Content-Type: text/html;charset=utf-8").append(CRLF);
        header.append("Date:").append(new Date()).append(CRLF);
        header.append("Content-Length:").append(len).append(CRLF);
        header.append(CRLF);
    }

    public void send(int code) {
        try {
            createHeader(code);
            bw.append(header);
            bw.append(content);
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
