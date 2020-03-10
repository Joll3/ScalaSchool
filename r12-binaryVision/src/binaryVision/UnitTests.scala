
package binaryVision

import org.junit.Test
import org.junit.Assert._

class UnitTests {

  @Test def testValidate() {
    val g = new util.Random(123)
    val repeats   = 10
    val trainSize = 40
    val testSize  = 100
    val accuracy  = 0.9    // must get this fraction right on every repeat
    assertTrue("testValidate() failed",
      (1 to repeats).forall(j => {
        print("Repeat %d (of %d) ... ".format(j, repeats))
        val shuffle = g.shuffle((0 until data.n).toList).toArray
        val trainIdx = shuffle.take(trainSize)
        val testIdx  = shuffle.drop(trainSize).take(testSize)
        val trainDigits = trainIdx.map(data.digits(_))
        val trainLabels = trainIdx.map(data.labels(_))
        val testDigits = testIdx.map(data.digits(_))
        val testLabels = testIdx.map(data.labels(_))
        val trainClassifier = classifier.train(trainDigits, trainLabels)
        val testClasses = testDigits.map(trainClassifier(_))
        val good = (testClasses zip testLabels).map(x => if(x._1 == x._2) 1 else 0).sum
        val goodFrac = good.toDouble/testSize       
        println("%.2f%% accuracy".format(goodFrac*100.0))
        goodFrac >= accuracy
      } ) 
    )
  }
}

