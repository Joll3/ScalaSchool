

package object frequencies {
  /*
   * NOTE: this is an exercise on functional programming style.
   * Therefore, you are NOT allowed to use mutable data structures or vars.
   */

  /**
   * Given a sequence seq of items of type T,
   * returns a map that associates each item in the sequence to the number times it
   * occurs in the sequence.
   * The resulting map should be undefined for all the items that do not appear in the sequence.
   * Hint: study the methods of http://www.scala-lang.org/api/current/index.html#scala.collection.Seq
   * and http://www.scala-lang.org/api/current/index.html#scala.collection.Map,
   * for instance, groupBy and mapValues may be useful.
   */
  def frequencies[T](seq: Seq[T]): Map[T, Int] = seq.map(T => (T, seq.count(_ == T))).toMap
    
  /**
   * Given a sequence seq of items of type T,
   * returns a map that associates, to each positive integer i, the set of items
   * that occur i times in the sequence.
   * If no item occurs i times in the sequence, then the map must be undefined for that i.
   */
  def freqToItems[T](seq: Seq[T]): Map[Int, Set[T]] = {
    val funktio = frequencies(seq)
    funktio.keys.groupBy(T => funktio(T)).mapValues(_.toSet)
  }
    //seq.map(T => (seq.count(x => x == T)), seq)
  
  /**
   * Given a sequence seq of items of type T,
   * returns the set of all items that occur most frequently in the sequence.
   * If the sequence is empty, then (and only then) the resulting set should be empty.
   */
  def mostFrequent[T](seq: Seq[T]): Set[T] = {
    val maxcount = 0
    if(seq.size != 0) {
      val f = freqToItems(seq)
      f(f.keys.toSeq.max)
    }
    else Set()
    //freqToItems(seq).values.find(_.size == maxcount).getOrElse(Set())
    //freqToItems(seq).filter(p=> p._2.size == maxcount).map(f=>(f._2)).toSet
    
  }
}

