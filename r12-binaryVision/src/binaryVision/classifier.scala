
package binaryVision

/*
 * Your task is here. But run 'TrainBrowser' in 'browsers.scala' first.
 *
 */

/*
 * YOUR TASK is to teach the machine to see (!) handwritten binary digits.
 *
 * Below is a skeleton for a function that __trains__ and returns
 * a __classifier function__ that in effect enables the machine to "see"
 * binary digits based on prior experience (the "training").
 *
 * The function 'train' takes as input __training digits__
 * (an array of digits, each of which is an Array of 25600 Doubles) and
 * the __labels__ of the training digits (each an Int, either 0 or 1).
 * Put otherwise, for each j we have that labels(j) tells whether
 * digits(j) is a 0 or 1. The training function returns a classifier function.
 *
 * A __classifier function__ takes as input a digit (an Array of 25600 Doubles)
 * and returns its very best guess whether it "sees" a 0 or 1 in the input.
 *
 * Complete the skeleton to give the machine the gift of sight.
 * (After you are done, run object ResultBrowser to witness whether
 * the machine can "see" -- what the machine "sees" is displayed in magenta.
 * Use the plus and minus buttons to browse.)
 *
 * Hints:
 * Instead of working with the digits directly, it may be a good idea
 * to work with __feature vectors__ computed from the digits.
 * (See object 'feature' further below.)
 * You can see from the code below that during training we pre-compute
 * a feature vector for each digit in the training data.
 * When the classifier function is invoked with an unknown
 * digit, you can compute its feature vector and compare it with the
 * feature vectors in the training set. For example, a __1-nearest neighbor__
 * classifier selects the __closest__ feature vector in the training
 * data, and returns the label of the closest vector as its best guess
 * as to whether the unknown digit is a 0 or 1. Euclidean distance can be
 * used to measure distance between feature vectors.
 * http://en.wikipedia.org/wiki/Euclidean_distance
 *
 * Remark:
 * Your are welcome to scrap our feature vectors if you can come up with
 * a more efficient solution that has less than 1600 dimensions.
 * (This is not difficult.) Similarly, you should not feel constrained
 * to use a nearest-neighbor classifier.
 *
 */

object classifier {
  val sizex = 160      // width of digits (do not edit)
  val sizey = 160      // height of digits (do not edit)
  val m = sizex*sizey  // length of digit array (=25600) (do not edit)

  def train(digits: Array[Array[Double]],
            labels: Array[Int]) : (Array[Double]) => Int = {
    val features = digits.map(feature.get(_))
    def classifyDigit(digit: Array[Double]) : Int = {
      // return the very best guess as to whether 'digit' is a 0 or 1
      ???
    }
    classifyDigit // return the classifier
  }
}

/*
 * We give you some help in your task. Namely, the object 'feature' below
 * defines a function 'get' to compute __feature vectors__ from the
 * grayscaled handwritten digits. A __feature vector__ is a
 * low(er)-dimensional representation of the original input.
 * The function consists of three simple steps
 * (indeed, rather than do anything fancy, our aim is to be able
 * to visualize the feature vectors as smaller images in the data browser):
 *
 * 1) Down-sample by a factor of four to reduce dimension
 *    (the original digits are 160-by-160 pixels, the feature
 *     vectors have 40-by-40 entries).
 *
 * 2) Do biased rounding to articulate the digit
 *    (all pixels that are >= 80% of the average grayscale become 0.0,
 *    all remaining pixels become 1.0).
 *
 * 3) Center the 40-by-40 array at the __barycenter__ of the 1.0-pixels,
 *    and complement the 1.0s to 0.0s and vice versa.
 *    (The centering at the barycenter eases e.g. nearest neighbor search.)
 *
 */

object feature {
  val h = 4
  val sizex = classifier.sizex/h
  val sizey = classifier.sizey/h
  val m = sizex*sizey

  def get(digit: Array[Double]) = {
    val down = new Array[Double](m)
    var i = 0
    while(i < m) {
      down(i) = digit((((i/(sizex))*sizex*h*h)+h*(i%(sizex))))
      i = i+1
    }
    val average = down.sum/m
    val step = down.map(v => if(v >= 0.8*average) { 0.0 } else { 1.0 })
    val total = step.sum
    val barx = math.round((0 until m).map(j => step(j)*(j%sizex)).sum/total).toInt
    val bary = math.round((0 until m).map(j => step(j)*(j/sizex)).sum/total).toInt
    val vec = new Array[Double](m)
    i = 0
    while(i < sizey) {
      var j = 0
      val bi = (i+bary+sizey+sizey/2)%sizey
      while(j < sizex) {
        val bj = (j+barx+sizex+sizex/2)%sizex
        vec(i*sizex+j) = 1.0-step(bi*sizex+bj)
        j = j+1
      }
      i = i+1
    }
    vec
  }
}


