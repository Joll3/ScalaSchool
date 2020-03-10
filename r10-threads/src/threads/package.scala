
package object threads {
  def invokeAll(work: Seq[() => Int]): Seq[Int] = {
    val results = new Array[Int](work.length) // return values of work functions
    ???
    results
  }

  def invokeAny(work: Seq[() => Int]): Int = {
    @volatile var result = 0 // set to return value of any work function
    val main = Thread.currentThread()
    ???
    Thread.interrupted() // clear interrupt status of current thread
    result
  }
}

