package Day_02;

import java.io.*;

//复制多级文件夹(这个复制是完完全全复制，名字都相同）

public class MutliLevelDirectory {
    public static void main(String[] args) throws IOException {
//       源目录地址
        File OriginFile = new File("C:\\Users\\DELL\\Desktop\\test");

//        目标的地址
        File DestFile = new File("C:\\Users\\DELL\\Desktop\\杂");
//        调用递归函数CopyFolder来实现复制过程
        CopyFolder(OriginFile, DestFile);
    }

//       通过递归来实现了多级目录的复制
    private static void CopyFolder(File OriginFile, File DestFile) throws IOException {
//       先判断是不是目录
        if (OriginFile.isDirectory()) {
//       下一步是通过组合构建复制过去的目录
            File NewFolder = new File(DestFile, OriginFile.getName());
            if (!NewFolder.exists()){
                NewFolder.mkdir();
            }
//       找出传进来的OriginFile里面所有的目录和文件（Attention：files里可能既有目录又有文件)
            File[] files = OriginFile.listFiles();
//      然后对files数组进行遍历，再次进入到CopyFolder函数中，进入递归
            for (File f : files) {
                CopyFolder(f, NewFolder);
            }
        } else {
            File newFile = new File(DestFile, OriginFile.getName());
            CopyFile(OriginFile, newFile);
        }
    }
//        实际复制文件的函数
    private static void CopyFile(File originFile, File destFile) throws IOException {
        BufferedOutputStream bops = new BufferedOutputStream(new FileOutputStream(destFile));
        BufferedInputStream bips = new BufferedInputStream(new FileInputStream(originFile));
        byte[] bytes = new byte[1024];
        while ((bips.read(bytes)) != -1) {
            bops.write(bytes);
        }
        bops.close();
        bips.close();
    }
}
