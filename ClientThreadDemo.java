package Day_05;

import java.io.*;
import java.net.Socket;

//多线程上传文件到服务器的客户端

public class ClientThreadDemo {
    public static void main(String[] args) throws IOException {
//       构建TCP传输端
        Socket socket = new Socket("192.168.1.199", 10010);
//       构建TCP传输流
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//      构建本地文件读取流
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\test\\穷爸爸富爸爸.txt"));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        socket.shutdownOutput();

//        构建接受反馈的TCP流
        BufferedReader bufferedReaderTCP = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = bufferedReaderTCP.readLine();
        System.out.println("服务器：" + result);

        bufferedReader.close();
        socket.close();
    }
}
