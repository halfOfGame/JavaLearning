package Spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.HashPartitioner

object CombineAndRemoveRepetition {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CombineAndRemoveRepetition").setMaster("local")
    val sc = new SparkContext(conf)

    //输入文件fileA.txt和fileB.txt已保存在本地文件系统/root/step1_files目录中
    val dataFile = "file:///root/step1_files"
    val data = sc.textFile(dataFile, 2)


    //第一步：执行过滤操作，把空行丢弃。
    val rdd1 = data.filter(_.trim().length > 0)


    //第二步：执行map操作，取出RDD中每个元素，去除尾部空格并生成一个(key, value)键值对。
    val rdd2 = rdd1.map(line => (line.trim, ""))


    //第三步：执行groupByKey操作，把所有key相同的value都组织成一个value-list。
    val rdd3 = rdd2.groupByKey()


    //第四步：对RDD进行重新分区，变成一个分区，
    //在分布式环境下只有把所有分区合并成一个分区，才能让所有元素排序后总体有序。
    val rdd4 = rdd3.partitionBy(new HashPartitioner(1))


    //第五步：执行sortByKey操作，对RDD中所有元素都按照key的升序排序。
    val rdd5 = rdd4.sortByKey()


    //第六步：执行keys操作，将键值对RDD中所有元素的key返回，形成一个新的RDD。
    val rdd6 = rdd5.keys


    //第七步：执行collect操作，以数组的形式返回RDD中所有元素。
    val rdd7 = rdd6.collect()


    //第八步：执行foreach操作，并使用println打印出数组中每个元素的值。
    println("") //注意：此行不要修改，否则会影响测试结果，在此行之后继续完成第八步的代码。
    rdd7.foreach(println)


  }
}
