package server;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ServerThread
 * @Description:
 * @Author: liuhefei
 * @Date: 2019/6/23
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class ServerThread implements Runnable {

    private static Map<String, String> contentMap = new HashMap<>();

    //可以参照Tomcat的web.xml配置文件
    static {
        contentMap.put("html", "text/html");
        contentMap.put("htm", "text/html");
        contentMap.put("jpg", "image/jpeg");
        contentMap.put("jpeg", "image/jpeg");
        contentMap.put("gif", "image/gif");
        contentMap.put("js", "application/javascript");
        contentMap.put("css", "text/css");
        contentMap.put("json", "application/json");
        contentMap.put("mp3", "audio/mpeg");
        contentMap.put("mp4", "video/mp4");
    }

    private Socket client;
    private InputStream in;
    private OutputStream out;
    private PrintWriter pw;
    private BufferedReader br;

    private static final String webroot = "F:\\webroot\\";

    public ServerThread(Socket client){
        this.client = client;
        init();
    }

    private void init(){
        //获取输入输出流
        try {
            in = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            gorun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gorun() throws Exception {
        //读取请求内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine().split(" ")[1].replace("/", "\\");  //请求的资源
        if(line.equals("\\")){
            line += "index.html";
        }
        System.out.println(line);
        String strType = line.substring(line.lastIndexOf(".")+1, line.length());  //获取文件的后缀名
        System.out.println("strType = " + strType);

        //给用户响应
        PrintWriter pw = new PrintWriter(out);
        InputStream input = new FileInputStream(webroot + line);

        //BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        pw.println("HTTP/1.1 200 ok");
        pw.println("Content-Type: "+ contentMap.get(strType)  +";charset=utf-8");
        pw.println("Content-Length: " + input.available());
        pw.println("Server: hello");
        pw.println("Date: " + new Date());
        pw.println();
        pw.flush();

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = input.read(bytes)) != -1){
            out.write(bytes, 0, len);
        }
        pw.flush();

        input.close();
        pw.close();
        reader.close();
        out.close();

        client.close();
    }
}
