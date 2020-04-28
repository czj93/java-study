package cn.caozj.ioc;

import java.io.*;

public class IOUtils {

//    public static void main(String[] args){
//        byte[] datas = fileToByte("resource/baidu.html");
//        byteArrayToFile(datas, "resource/copy/", "baidu_copy.html");
//    }

    // 将文件转成字节数组
    public static byte[] fileToByte(String filePath){
        File src = null;

        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try{
            src = new File(filePath);
            in = new FileInputStream(src);
            baos = new ByteArrayOutputStream();

            byte[] flush = new byte[1024*10];
            int len = -1;
            while ((len = in.read(flush, 0, flush.length)) != -1){
                baos.write(flush, 0, len);
            }
            baos.flush();
            return baos.toByteArray();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(baos != null){
                    baos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void byteArrayToFile(byte[] datas, String filePath, String fileName){

        OutputStream out = null;
        File src = null;
        try{
            File dir = new File(filePath);
            if(!dir.exists()  && !dir.isDirectory()){
                dir.mkdirs();
            }
            src = new File(filePath + fileName);
            out = new BufferedOutputStream(new FileOutputStream(src));
            out.write(datas);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(out != null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
