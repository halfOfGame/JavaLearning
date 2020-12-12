package JavaInterview;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author halfOfGame
 * @create 2020-04-02,12:56
 */

//一个Java进程中包含的线程
public class MultiThread {
    public static void main(String[] args) {
        //获取Java线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() + ". " + threadInfo.getThreadName());
        }
    }

}
