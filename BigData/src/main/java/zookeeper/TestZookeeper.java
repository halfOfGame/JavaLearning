package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class TestZookeeper {

    private static String connectString = "127.0.0.1:2181";
    private static int sessionTimeout = 5000;
    private static ZooKeeper zkClient = null;

    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

                // 收到事件通知后的回调函数（用户的业务逻辑）
                 System.out.println(event.getType() + "--" + event.getPath());

                // 再次启动监听
//                try {
//                    zkClient.getChildren("/", true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    // 创建子节点
    public void create(String node, String value) throws Exception {
        // 参数1：要创建的节点的路径； 参数2：节点数据 ； 参数3：节点权限 ；参数4：节点的类型
        String nodeCreated = zkClient.create(node, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 获取子节点
    public void getChildren(String path) throws Exception {

        List<String> children = zkClient.getChildren(path, true);
        for (String child : children) {
            System.out.println(child);
        }

        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    // 判断znode是否存在
    public void exist(String path) throws Exception {

        Stat stat = zkClient.exists(path, false);
        System.out.println(stat == null ? "not exist" : "exist");
    }

    public static void main(String[] args) throws Exception {
        TestZookeeper testZookeeper = new TestZookeeper();
        testZookeeper.init();
        testZookeeper.create("/key", "value");
        testZookeeper.exist("/key");
    }
}
