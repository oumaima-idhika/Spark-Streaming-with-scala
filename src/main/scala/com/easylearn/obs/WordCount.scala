package com.easylearn.obs ;


import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/** Count up how many of each word appears in a book as simply as possible. */
object WordCount {

  /** Our main function where the action happens */
  def main(args: Array[String]) {


    // Create a SparkContext using every core of the local machine
    val conf = new SparkConf().setMaster("local[*]").setAppName("lab1")
    val sc = new SparkContext(conf)
    // Read each line of my book into an RDD
    val input = sc.textFile("../book.txt")

    // Split into words separated by a space character
    val words = input.flatMap(x => x.split(" "))

    // Count up the occurrences of each word
    val wordCounts = words.countByValue()

    // Print the results.
    wordCounts.foreach(println)
  }

}
