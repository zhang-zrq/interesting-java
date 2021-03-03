package Day_05;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    public Socket accept;

    public ServerThread(Socket accept) {
        this.accept = accept;
    }

//    重写的run启动线程的方法
//   根据经验：多个线程的共用的代码部分一般都放在run()函数里面。
    @Override
    public void run() {
        try {
//            构建TCP接收端
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

//            构建本地文件存储流。
//               首先解决文件重名问题，不能每个线程的都往一个文件中复制，这不现实
            int count = 0;
            File file = new File("C:\\Users\\DELL\\Desktop\\test\\prepare_" + count + ".txt");
//           判断文件是否存在，如果存在的话一直++，知道不存在为止
            while (file.exists()) {
                count++;
                file = new File("C:\\Users\\DELL\\Desktop\\test\\prepare_" + count + ".txt");
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

//        构建TCP反馈流，向每个客户端传递信息。
            BufferedWriter bufferedWriterTCP = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
            bufferedWriterTCP.write("文件上传成功");
            bufferedWriterTCP.newLine();
            bufferedWriterTCP.flush();

//            释放资源
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
