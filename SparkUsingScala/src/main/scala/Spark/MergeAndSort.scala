package Spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.HashPartitioner

object MergeAndSort {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("FileSort").setMaster("local")
    val sc = new SparkContext(conf)

    //输入文件file1.txt、file2.txt和file3.txt已保存在本地文件系统/root/step2_files目录中
    val dataFile = "file:///root/step2_files"
    val data = sc.textFile(dataFile, 3)


    //第一步：执行过滤操作，把空行丢弃。
    val rdd1 = data.filter(_.trim().length > 0)


    //第二步：执行map操作，取出RDD中每个元素，去除尾部空格并转换成整数，生成一个(key, value)键值对。
    val rdd2 = rdd1.map(line => (line.trim.toInt, ""))


    //第三步：对RDD进行重新分区，变成一个分区，
    //在分布式环境下只有把所有分区合并成一个分区，才能让所有元素排序后总体有序。
    val rdd3 = rdd2.partitionBy(new HashPartitioner(1))


    //第四步：执行sortByKey操作，对RDD中所有元素都按照key的升序排序。
    val rdd4 = rdd3.sortByKey()


    //第五步：执行keys操作，将键值对RDD中所有元素的key返回，形成一个新的RDD。
    val rdd5 = rdd4.keys


    //第六步：执行map操作，取出RDD中每个元素，生成一个(key, value)键值对，
    //其中key是整数的排序位次，value是原待排序的整数。
    var index = 0
    val rdd6 = rdd5.map(t => {
      index = index + 1
      (index, t)
    })


    //第七步：执行collect操作，以数组的形式返回RDD中所有元素。
    val rdd7 = rdd6.collect()


    //第八步：执行foreach操作，依次遍历数组中每个元素，分别取出(key, value)键值对中key和value，
    //按如下格式输出：key value
    println("") //注意：此行不要修改，否则会影响测试结果，在此行之后继续完成第八步的代码。
    rdd7.foreach(t => println(t._1 + " " + t._2))


  }
}

