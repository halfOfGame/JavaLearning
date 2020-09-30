package HDFS;

import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class CommonOperation {

    public static Configuration conf;
    public static FileSystem fs;



    //初始化
    public static void init() {

    }

    //写入数据
    public static void writeData(Path filePath, String content) throws IOException {
        FSDataOutputStream outputStream = fs.create(filePath);
        outputStream.writeUTF("https://www.educoder.net");
        outputStream.close();
    }

    //读取数据
    public static String readData(FileSystem fs, Path filePath) throws IOException {
        FSDataInputStream inputStream = fs.open(filePath);
        String data = inputStream.readUTF();
        inputStream.close();
        return data;
    }

    //判断路径是否存在
    public static boolean testFileExists(String path) throws IOException {
        return fs.exists(new Path(path));
    }

    //上传文件到指定路径，若文件存在则覆盖
    public static void copyFromLocalFile(String localFilePath, String remoteFilePath) throws IOException {
        Path localPath = new Path(localFilePath);
        Path remotePath = new Path(remoteFilePath);
        //第一个参数表示是否删除源文件，第二个参数表示是否覆盖
        fs.copyFromLocalFile(false, true, localPath, remotePath);
    }

    //下载文件到本地
    public static void copytoLocalFile(String remoteFilePath, String localFilePath) throws IOException {
        Path remotePath = new Path(remoteFilePath);
        File f = new File(localFilePath);
        if (f.exists()) {
            System.out.println(localFilePath + "已存在，自动重命名");
            Integer i = 0;
            while (true) {
                f = new File(localFilePath + "_" + i.toString());
                if (!f.exists()) {
                    localFilePath = localFilePath + "_" + i.toString();
                    break;
                }
                i++;
            }
            System.out.println("文件已重命名为：" + localFilePath);
        }
        Path localPath = new Path(localFilePath);
        fs.copyToLocalFile(remotePath, localPath);
    }

    //上传文件到指定远程路径中，追加文件内容
    public static void appendToFile(String localFilePath, String remoteFilePath) throws IOException {
        Path remotePath = new Path(remoteFilePath);
        FileInputStream in = new FileInputStream(localFilePath);
        FSDataOutputStream out = fs.append(remotePath);
        byte[] data = new byte[1024];
        int read = -1;
        while ((read = in.read(data)) > 0) {
            out.write(data, 0, read);
        }
        out.close();
        in.close();
    }

    //从指定远程路径中读取文件，输出到控制台
    public static void display(String remoteFilePath) throws IOException {
        Path remotePath = new Path(remoteFilePath);
        FSDataInputStream in = fs.open(remotePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
        in.close();
    }

    //删除指定文件
    public static boolean removeRemoteFile(String remoteFilePath) throws IOException {
        Path remotePath = new Path(remoteFilePath);
        //第二个参数表示是否可以删除目录
        return fs.delete(remotePath, false);
    }


    public static void main(String[] args) throws IOException {
        conf = new Configuration();
        conf.set("fs.defaultFs", "hdfs://localhost:9000");
        conf.set("hs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        fs = FileSystem.get(conf);

        fs.close();
    }
}
