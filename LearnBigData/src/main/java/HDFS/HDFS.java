package HDFS;

/**
 * @author halfOfGame
 * @create 2020-03-17,15:46
 */

import java.io.*;
import java.sql.Date;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFS {

    /**
     * 判断路径是否存在
     */
    public static boolean test(Configuration conf, String path) throws IOException {
        /*****start*****/
        //请在此处编写判断文件是否存在的代码
        FileSystem fs = FileSystem.get(conf);
        return fs.exists(new Path(path));
        /*****end*****/
    }

    /**
     * 复制文件到指定路径
     * 若路径已存在，则进行覆盖
     */
    public static void copyFromLocalFile(Configuration conf, String localFilePath, String remoteFilePath) throws IOException {
        /*****start*****/
        //请在此处编写复制文件到指定路径的代码
        FileSystem fs = FileSystem.get(conf);
        Path localPath = new Path(localFilePath);
        Path remotePath = new Path(remoteFilePath);
        fs.copyFromLocalFile(false, true, localPath, remotePath);
        /*****end*****/
    }

    /**
     * 追加文件内容
     */
    public static void appendToFile(Configuration conf, String localFilePath, String remoteFilePath) throws IOException {
        /*****start*****/
        //请在此处编写追加文件内容的代码
        FileSystem fs = FileSystem.get(conf);
        Path remotePath = new Path(remoteFilePath);
        FileInputStream in = new FileInputStream(localFilePath);
        FSDataOutputStream out = fs.append(remotePath);
        byte[] data = new byte[1024];
        int read = -1;
        while ((read = in.read(data)) > 0) {
            out.write(data, 0, read);
        }
        out.close();
        /*****end*****/
    }

    /**
     * 主函数
     */
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        createHDFSFile(conf);
        String localFilePath = "./file/text.txt";            // 本地路径
        String remoteFilePath = "/user/hadoop/text.txt";    // HDFS路径
        String choice = "";

        FileWriter fileWriter = new FileWriter(new File("/tmp/output/text.txt"));

        try {
            /* 判断文件是否存在 */
            Boolean fileExists = false;
            if (HDFS.test(conf, remoteFilePath)) {
                fileExists = true;
                System.out.println(remoteFilePath + " 已存在.");
                choice = "append";        //若文件存在则追加到文件末尾
            } else {
                System.out.println(remoteFilePath + " 不存在.");
                choice = "overwrite";    //覆盖
            }

            /*****start*****/
            //请在此处编写文件不存在则上传 文件choice等于overwrite则覆盖   choice 等于append 则追加的逻辑

            if (!fileExists) { // 文件不存在，则上传

                System.out.println(localFilePath + " 已上传至 " + remoteFilePath);
            } else if ("overwrite".equals(choice)) {    // 选择覆盖

                System.out.println(localFilePath + " 已覆盖 " + remoteFilePath);
            } else if ("append".equals(choice)) {   // 选择追加

                System.out.println(localFilePath + " 已追加至 " + remoteFilePath);
            }
            /*****end*****/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建HDFS文件
    public static void createHDFSFile(Configuration conf) throws IOException {
        FileSystem fs = FileSystem.get(conf);  //获取文件系统
        Path file = new Path("/user/hadoop/text.txt");        //创建文件
        FSDataOutputStream outStream = fs.create(file); //获取输出流
        outStream.writeUTF("hello");
        outStream.close();
        fs.close();
    }
}
