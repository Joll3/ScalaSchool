
package words

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object run {

  def main(args: Array[String]): Unit = {

    /*
     * Let us start by setting up a Spark context which runs locally
     * using two worker threads.
     *
     * Here we go:
     *
     */

    val sc = new SparkContext("local[2]", "words")

    /*
     * Next, let us set up our input. 
     * First, we require a path in the file system where to find
     * the input files. In case you happen to be at Maari, just
     * let what is below be as is; otherwise, set up the path
     * to your files below: 
     *
     */

    val path = ""

    /*
     * After the path is configured, we need to decide which input
     * file to look at. There are two choices -- you should test your
     * code with "War and Peace" (default below), and then use the code with
     * "Wealth of Nations" to compute the correct solutions 
     * (which you will submit to A+ for grading).
     * 
     */

    // Tolstoy -- War and Peace         (test input)
    //val filename = path ++ "pg2600.txt"

    // Smith   -- Wealth of Nations     (uncomment line below to use as input)
    val filename = path ++ "pg3300.txt"

    /*
     * Now you may want to open up in a web browser 
     * the Scala programming guide for 
     * Spark version 2.3.0 (-- the version installed at Maari):
     *
     * http://spark.apache.org/docs/2.3.0/programming-guide.html
     * 
     */

    /*
     * Let us now set up an RDD from the lines of text in the file:
     *
     */

    val lines: RDD[String] = sc.textFile(filename)

    /* The following requirement sanity-checks the number of lines in the file 
     * -- if this requirement fails you are in trouble.
     */

    require((filename.contains("pg2600.txt") && lines.count() == 65007) ||
            (filename.contains("pg3300.txt") && lines.count() == 35600))

    /* 
     * Let us make one further sanity check. That is, we want to
     * count the number of lines in the file that contain the 
     * substring "rent".
     *
     */

    val lines_with_rent: RDD[String] = 
      lines.filter(line => line.contains("rent"))

    val rent_count = lines_with_rent.count()
    println("OUTPUT: \"rent\" occurs on %d lines in \"%s\""
              .format(rent_count, filename))
    require((filename.contains("pg2600.txt") && rent_count == 360) ||
            (filename.contains("pg3300.txt") && rent_count == 1443))

    /*
     * All right, if the execution continues this far without
     * failing a requirement, we should be pretty sure that we have
     * the correct file. Now we are ready for the work that you need
     * to put in. 
     *
     */

    /*
     * Spark operates by __transforming__ RDDs. For example, above we
     * took the RDD 'lines', and transformed it into the RDD 'lines_with_rent'
     * using the __filter__ transformation. 
     *
     * Important: 
     * While the code that manipulates RDDs may __look like__ we are
     * manipulating just another Scala collection, this is in fact
     * __not__ the case. An RDD is an abstraction that enables us 
     * to easily manipulate terabytes of data in a cluster computing
     * environment. In this case the dataset is __distributed__ across
     * the cluster. In fact, it is most likely that the entire dataset
     * cannot be stored in a single cluster node. 
     *
     * Let us practice our skills with simple RDD transformations. 
     *
     */

    /*
     * Task 1:
     * This task asks you to transform the RDD
     * 'lines' into an RDD 'depunctuated_lines' so that __on each line__, 
     * all occurrences of any of the punctuation characters
     * ',', '.', ':', ';', '\"', '(', ')', '{', '}' have been deleted.
     *
     * Hint: it may be a good idea to consult 
     * http://www.scala-lang.org/api/2.12.2/scala/collection/immutable/StringOps.html
     *
     */

    val depunctuated_lines: RDD[String] = {
      val list = Array(',', '.', ':', ';', '\"', '(', ')', '{', '}')
      lines.map(f => f.filterNot(list.contains(_)))

    }
    /* 
     * Let us now check and print out data that you want to 
     * record (__when the input file is "pg3300.txt"__) into 
     * the file "solutions.scala" that you need to submit for grading
     * together with this file. 
     */
    
    val depunctuated_length = depunctuated_lines.map(_.length).reduce(_ + _)
    println("OUTPUT: total depunctuated length is %d".format(depunctuated_length))
    require(!filename.contains("pg2600.txt") || depunctuated_length == 3069444)


    /*
     * Task 2:
     * Next, let us now transform the RDD of depunctuated lines to
     * an RDD of consecutive __tokens__. That is, we want to split each
     * line into zero or more __tokens__ where a __token__ is a 
     * maximal nonempty sequence of non-space (non-' ') characters on a line. 
     * Blank lines or lines with only space (' ') in them should produce 
     * no tokens at all. 
     *
     * Hint: Use either a map or a flatMap to transform the RDD 
     * line by line. Again you may want to take a look at StringOps
     * for appropriate methods to operate on each line. Use filter
     * to get rid of blanks as necessary.
     *
     */

    val tokens: RDD[String] = {
      depunctuated_lines.filter(x => !x.filterNot(_ == ' ').isEmpty).flatMap(f => f.split(' '))                    // transform 'depunctuated_lines' to tokens
    }

    /* ... and here comes the check and the printout. */

    val token_count = tokens.count()
    println("OUTPUT: %d tokens".format(token_count))
    require(!filename.contains("pg2600.txt") || token_count == 566315)


    /*
     * Task 3:
     * Transform the RDD of tokens into a new RDD where all upper case 
     * characters in each token get converted into lower case. Here you may 
     * restrict the conversion to characters in the Roman alphabet 
     * 'A', 'B', ..., 'Z'.
     *
     */

    val tokens_lc: RDD[String] = 
      tokens                       // map each token in 'tokens' to lower case


    /* ... and here comes the check and the printout. */

    val tokens_a_count = tokens.flatMap(t => t.filter(_ == 'a')).count()
    println("OUTPUT: 'a' occurs %d times in tokens".format(tokens_a_count))
    require(!filename.contains("pg2600.txt") || tokens_a_count == 199232)

    /*
     * Task 4:
     * Transform the RDD of lower-case tokens into a new RDD where
     * all but those tokens that consist only of lower-case characters
     * 'a', 'b', ..., 'z' in the Roman alphabet have been filtered out.
     * Let us call the tokens that survive this filtering __words__.
     *
     */

    val words: RDD[String] = 
      tokens                        // filter out all but words from 'tokens_lc'


    /* ... and here comes the check and the printout. */

    val words_count = words.count()
    println("OUTPUT: %d words".format(words_count))
    require(!filename.contains("pg2600.txt") || words_count == 547644)


    /*
     * Now let us move beyond maps, filtering, and flatMaps 
     * to do some basic statistics on words. To solve this task you
     * can consult the Spark programming guide, examples, and API:
     *
     * http://spark.apache.org/docs/2.3.0/programming-guide.html
     * http://spark.apache.org/examples.html 
     * http://spark.apache.org/docs/2.3.0/api/scala/index.html#org.apache.spark.package
     */

    /*
     * Task 5:
     * Count the number of occurrences of each word in 'words'.
     * That is, create from 'words' by transformation an RDD
     * 'word_counts' that consists of, ___in descending order___, 
     * pairs (c,w) such that w occurs exactly c times in 'words'. 
     * Then take the 100 most frequent words in this RDD and 
     * answer the following two questions (first is practice with
     * a given answer for "pg2600.txt", the second question is
     * the one where you need to find the answer yourself and
     * submit it for grading).
     *
     * Practice question for "pg2600.txt" (answer given below):
     * What word occurs exactly 1772 times in 'words' ? 
     * (answer: "pierre")
     *
     * The question that you need to answer for "pg3300.txt":
     * What word occurs exactly 777 times in 'words' ?
     * (give your answer in lower case)
     *
     */

    val word_counts: RDD[(Long,String)] = 
      ???


    /* ... and here comes a check. */

    val top_word = word_counts.take(1)(0)
    println("OUTPUT: top word is %s (%d times)".format(top_word._2, top_word._1))
    require(!filename.contains("pg2600.txt") || (top_word._2 == "the" && top_word._1 == 34558))

    /* ... print out the 100 most frequent words. */
  
    println("OUTPUT: The 100 most frequent words are, in rank order ...")
    word_counts.take(100)
               .zipWithIndex
               .foreach(x => 
                  println("OUTPUT: %3d: \"%s\" with %d occurrences".
                            format(x._2+1,x._1._2,x._1._1)))

    /* That's it! */

  }
}

