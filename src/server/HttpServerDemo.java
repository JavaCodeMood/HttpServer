package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @ClassName: HttpServer
 * @Description:
 * @Author: liuhefei
 * @Date: 2019/6/23
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class HttpServerDemo {
    public static void main(String[] args) throws IOException {
        //启动服务器，监听9005端口
        ServerSocket server = new ServerSocket(9005);
        System.out.println("服务器启动，监听9005端口....");
        while (!Thread.interrupted()){
            //不停接收客户端请求
            Socket client = server.accept();
            //获取输入输出流
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            //读取请求内容
            /*int len = 0;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1){
                System.out.println(new String(b, 0, len));
            }*/

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            System.out.println(line);

            //给用户响应
            PrintWriter pw = new PrintWriter(out);
            InputStream input = new FileInputStream("F:\\webroot\\index.html");

            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            pw.println("HTTP/1.1 200 ok");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println("Content-Length: " + input.available());
            pw.println("Server: hello");
            pw.println("Date: " + new Date());
            pw.println();
            pw.flush();
            String content = null;
            while ((content = buffer.readLine()) != null){
                pw.print(content);
            }
            pw.flush();

            buffer.close();
            input.close();
            pw.close();
            reader.close();
            out.close();
            in.close();
            client.close();
        }

        server.close();
    }
}
