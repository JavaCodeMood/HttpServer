package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: HttpServer
 * @Description:  服务端
 * @Author: liuhefei
 * @Date: 2019/6/23
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class HttpServer1 {
    public static void main(String[] args) throws IOException {
        //创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        //启动服务器，监听9005端口
        ServerSocket server = new ServerSocket(9005);
        System.out.println("服务器启动，监听9005端口....");
        while (!Thread.interrupted()){
            //不停接收客户端请求
            Socket client = server.accept();
            //向线程池中提交任务
            pool.execute(new ServerThread(client));
        }
        server.close();
        pool.shutdown();
    }
}
