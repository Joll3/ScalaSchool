
/*
 * Assignment:  Word operations
 *
 * Description:
 * This assignment asks you to implement common word operations that are
 * not available in the Scala programming language. The intent is to practice
 * your skills at working with bits.
 *
 */

package object wordOps {

  /*
   * Task 1: Population count (number of 1-bits in a word)
   *
   * Complete the following function that takes as parameter
   * a 32-bit word, w, and returns __the number of 1-bits__ in w.
   *
   */

  def popCount(w: Int): Int = {
    var counter = 0
    for (i <- 0 until 32){
    if ((w>>i&1)==1) counter+=1
    }
    counter
  }

  /*
   * Task 2: Reverse bit positions
   *
   * Complete the following function that takes as parameter
   * a 16-bit word, w, and returns a 16-bit word, r, such that
   * for every j=0,1,...,15,
   * the value of the bit at position j in r is equal to
   * the value of the bit at position 15-j in w.
   *
   */

  def reverse(w: Short): Short = {
    var storage = 0
    for(i <- 0 until 16){
      if((w>>i&1)==1){
        storage = (1<<(15-i))+storage
      }
    }
    storage.toShort
  }
  /*
   * Task 3: Left rotation
   *
   * Complete the following function that takes two parameters
   *
   * 1) a 64-bit word, w, and
   * 2) a 32-bit word, k.
   *
   * The function returns a 64-bit word, r, such that
   * for all j=0,1,...,63
   * the value of the bit at position (j+k)%64 in r is equal to
   * the value of the bit at position j in w.
   *
   */

  def leftRotate(w: Long, k: Int): Long = {
    var r = 0.toLong
    for(i <- 0 until 64){
      if((w>>i&1)==1){
        r = (1L<<((i+k)%64)) + r
      }
    }
    r
  }

}

