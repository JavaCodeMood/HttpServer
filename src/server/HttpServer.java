package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @ClassName: HttpServer
 * @Description:  服务端
 * @Author: liuhefei
 * @Date: 2019/6/23
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class HttpServer {
    public static void main(String[] args) throws IOException {
        //启动服务器，监听9005端口
        ServerSocket server = new ServerSocket(9005);
        System.out.println("服务器启动，监听9005端口....");
        while (!Thread.interrupted()){
            //不停接收客户端请求
            Socket client = server.accept();
            //开启线程
            new Thread(new ServerThread(client)).start();
        }
        server.close();
    }
}
