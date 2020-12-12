package MapReduce;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class simple_data_mining {
    public static int time = 0;

    /**
     * 输入一个child-parent的表格
     * 输出一个体现grandchild-grandparent关系的表格
     */
    //Map将输入文件按照空格分割成child和parent，然后正序输出一次作为右表，反序输出一次作为左表，需要注意的是在输出的value中必须加上左右表区别标志
    public static class Map extends Mapper<Object, Text, Text, Text> {
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] childAndParent = line.split(" ");
            List<String> list = new ArrayList<>(2);
            for (String childOrParent : childAndParent) {
                if (!"".equals(childOrParent)) {
                    list.add(childOrParent);
                }
            }
            if (!"child".equals(list.get(0))) {
                String childName = list.get(0);
                String parentName = list.get(1);
                String relationType = "1";
                context.write(new Text(parentName), new Text(relationType + "+" + childName + "+" + parentName));
                relationType = "2";
                context.write(new Text(childName), new Text(relationType + "+" + childName + "+" + parentName));
            }

        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            //输出表头
            if (time == 0) {
                context.write(new Text("grand_child"), new Text("grand_parent"));
                time++;
            }

            //获取value-list中value的child
            List<String> grandChild = new ArrayList<>();

            //获取value-list中value的parent
            List<String> grandParent = new ArrayList<>();

            //左表，取出child放入grand_child
            for (Text text : values) {
                String s = text.toString();
                String[] relation = s.split("\\+");
                String relationType = relation[0];
                String childName = relation[1];
                String parentName = relation[2];
                if ("1".equals(relationType)) {
                    grandChild.add(childName);
                } else {
                    grandParent.add(parentName);
                }
            }

            //右表，取出parent放入grand_parent
            int grandParentNum = grandParent.size();
            int grandChildNum = grandChild.size();
            if (grandParentNum != 0 && grandChildNum != 0) {
                for (int m = 0; m < grandChildNum; m++) {
                    for (int n = 0; n < grandParentNum; n++) {
                        context.write(new Text(grandChild.get(m)), new Text(
                                grandParent.get(n)));
                    }
                }

                //输出结果

            }
        }

        public static void main(String[] args) throws Exception {
            // TODO Auto-generated method stub
            Configuration conf = new Configuration();
            conf.set("fs.default.name", "hdfs://localhost:9000");
            String[] otherArgs = new String[]{"input", "output"}; /* 直接设置输入参数 */
            if (otherArgs.length != 2) {
                System.err.println("Usage: wordcount <in> <out>");
                System.exit(2);
            }
            Job job = Job.getInstance(conf, "Single table join");
            job.setJarByClass(simple_data_mining.class);
            job.setMapperClass(Map.class);
            job.setReducerClass(Reduce.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
            FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);

        }
    }
}