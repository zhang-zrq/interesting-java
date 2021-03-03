package Day_02;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

//复制单级文件夹(也就是这个不能复制那种目录套目录的）

public class SingleLevelDirectory {
    public static void main(String[] args) throws IOException {
//        源目录地址
        File origin = new File("C:\\Users\\DELL\\Desktop\\申报材料");
        String originFolder = origin.getName();
//        目标目录（目标目录是组合形成的）
        File destFolder = new File("C:\\Users\\DELL\\Desktop\\DEV", originFolder);
//        源目录里面的所有文件集合
        File[] originFiles = origin.listFiles();
//        判断目标文件夹是否为存在，不存在就新建
        if (!destFolder.exists()) {
            destFolder.mkdir();
        }
        for (File originFile : originFiles) {
            String fName = originFile.getName();
//            通过组合创建出源目录复制过去后的文件路径，（也就是将源目录的名字为test文件复制到另一个文件夹时候创建同名文件的过程）
            File destFile = new File(destFolder, fName);
//            然后直接调用函数
            copyFile(originFile, destFile);
        }
    }

    //具体复制每个文件的函数
    private static void copyFile(File originFile, File destFile) throws IOException {
//        字节缓冲流
        BufferedOutputStream bops = new BufferedOutputStream(new FileOutputStream(destFile));
        //FileOutputStream的构造函数里既可以是String对象，又可以直接是File
        BufferedInputStream bips = new BufferedInputStream(new FileInputStream(originFile));
        byte[] bytes = new byte[1024];
        while (bips.read(bytes) != -1) {
            bops.write(bytes);
        }
        bips.close();
        bops.close();
    }
}
