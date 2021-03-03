package Day_05;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//       多线程服务器端

public class ServerThreadDemo {
    public static void main(String[] args) throws IOException {
//        构建TCP接收端
        ServerSocket serverSocket = new ServerSocket(10010);

//      将服务器设置为一直开启的状态

        while (true) {
//        构建接受的TCP对象
            Socket accept = serverSocket.accept();
//        将上述的TCP对象传入到自定义的多线程类ServerThread中，然后启动
            new Thread(new ServerThread(accept)).start();

        }

    }
}
