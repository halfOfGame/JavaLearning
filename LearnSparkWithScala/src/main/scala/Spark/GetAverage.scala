package Spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object GetAverage {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("AvgScore").setMaster("local")
    val sc = new SparkContext(conf)

    //输入文件AlgorithmScore.txt、DataBaseScore.txt和PythonScore.txt已保存在本地文件系统/root/step3_files目录中
    val dataFile = "file:///root/step3_files"
    val data = sc.textFile(dataFile)


    //第一步：执行过滤操作，把空行丢弃。
    val rdd1 = data.filter(_.trim().length > 0)


    //第二步：执行map操作，取出RDD中每个元素(即一行文本)，以空格作为分隔符将一行文本拆分成两个字符串，
    //拆分后得到的字符串封装在一个数组对象中，成为新的RDD中一个元素。
    var rdd2 = rdd1.map(line => line.split(" "))


    //第三步：执行map操作，取出RDD中每个元素(即字符串数组)，取字符串数组中第一个元素去除尾部空格，
    //取字符串数组中第二个元素去除尾部空格并转换成整数，并由这两部分构建一个(key, value)键值对。
    val rdd3 = rdd2.map(t => (t(0).trim, t(1).trim.toInt))


    //第四步：执行mapValues操作，取出键值对RDD中每个元素的value，使用x=>(x,1)这个匿名函数进行转换。
    val rdd4 = rdd3.mapValues(x => (x, 1))


    //第五步：执行reduceByKey操作，计算出每个学生所有课程的总分数和总课程门数，x代表第一个元素，y代表第二个元素。
    val rdd5 = rdd4.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))


    //第六步：执行mapValues操作，计算出每个学生的平均成绩。
    val rdd6 = rdd5.mapValues(x => (x._1.toDouble / x._2))


    //第七步：执行collect操作，以数组的形式返回RDD中所有元素。
    val rdd7 = rdd6.collect()


    //第八步：执行foreach操作，按如下格式打印出每个学生的平均成绩：姓名 成绩，其中成绩要求保留两位小数。
    println("") //注意：此行不要修改，否则会影响测试结果，在此行之后继续完成第八步的代码。
    rdd7.foreach(t => {
      val x = t._2
      println(t._1 + " " + f"$x%1.2f")
    })

  }
}

