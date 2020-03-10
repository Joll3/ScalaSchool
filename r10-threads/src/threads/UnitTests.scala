
package threads

import org.junit.Test
import org.junit.Assert._

class UnitTests {

  def testSeq(n: Int, short: Int, long: Int, shortval: Int, longval: Int) = {
    val w = new Array[() => Int](n)
    val r = new util.Random(123)
    val res = new Array[Int](n)
    res(0) = shortval
    w(0) = () => { Thread.sleep(short); res(0) }
    (1 until n).foreach(i => res(i) = longval)
    (1 until n).foreach(i => w(i) = () => { Thread.sleep(long); res(i) })
    r.shuffle(w.toVector)
  }

  def withWallClock[T](work: =>T) = {
    val start = System.nanoTime
    val r = work
    val stop = System.nanoTime
    (r, (stop-start)/1e9)
  }

  @Test def testInvokeAll() {
    val work = testSeq(10, 500, 1000, 99, 123) 
      // longest work item takes about 1s (1000ms), wall clock time
    val (r, t) = withWallClock(invokeAll(work))
    assertTrue("invokeAll() gives bad output", 
               r.contains(99) && r.reduceLeft(_ + _) == 9*123+99)
    assertTrue("invokeAll() does not meet the performance requirement", 
               t <= 1.5) // ... so we'd better finish in 1.5s, wall clock time
  }

  @Test def testInvokeAny() {
    val work = testSeq(10, 500, 2000, 99, 123) 
      // shortest work item takes about 0.5s (500ms), wall clock time
    val (r, t) = withWallClock(invokeAny(work))
    assertTrue("invokeAny() gives bad output", 
               r == 99)
    assertTrue("invokeAny() does not meet the performance requirement", 
               t <= 1.0) // ... so we'd better finish in 1.0s, wall clock time
  }
}

