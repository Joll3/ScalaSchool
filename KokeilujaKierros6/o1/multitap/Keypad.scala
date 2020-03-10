
package o1.multitap

/**
 * Each instance of the class `KeyPad` represents a virtual keypad that supports multi-tap
 * input via numbered keys. See Chapter 6.1 in the course materials for details.
 *
 * @param charactersForKeys  a ten-element vector that contains the characters obtainable
 *                           by pressing the numbered keys from 0 to 9. For instance, if
 *                           the element at index 5 is "MNO", it means that pressing 5 once
 *                           produces M, pressing it twice produces N, and pressing it three
 *                           times produces O.
 */
class Keypad(val charactersForKeys: Vector[String]) {

  /**
   * Returns the character produced by pressing the given number key (from '0' to '9')
   * the given number of times on this keypad. If a key is pressed more times than there
   * are characters assigned to the key, the result "wraps around" (see Chapter 6.1).
   */
//  def charFor(keyPressed: Char, timesPressed: Int) = {
//    if (timesPressed == 6 || keyPressed == 8) {
//      if (timesPressed % 4 == 0) charactersForKeys(keyPressed.asDigit.toInt).drop(3).take(1)
//      else if (timesPressed % 4 == 1) charactersForKeys(keyPressed.asDigit.toInt).drop(2).take(1)
//      else if (timesPressed % 4 == 2) charactersForKeys(keyPressed.asDigit.toInt).drop(1).take(1)
//      else charactersForKeys(keyPressed.asDigit.toInt)take(1)
//
//    } else if (keyPressed == 0) {
//      if (timesPressed % 5 == 0) charactersForKeys(keyPressed.asDigit.toInt).drop(4).take(1)
//      else if (timesPressed % 5 == 1) charactersForKeys(keyPressed.asDigit.toInt).drop(3).take(1)
//      else if (timesPressed % 5 == 2) charactersForKeys(keyPressed.asDigit.toInt).drop(2).take(1)
//      else if (timesPressed % 5 == 2) charactersForKeys(keyPressed.asDigit.toInt).drop(1).take(1)
//      else charactersForKeys(keyPressed.asDigit.toInt).take(1)
//    }
//    else {
//      if (timesPressed % 3 == 0) charactersForKeys(keyPressed.asDigit.toInt).drop(2).take(1)
//      else if (timesPressed % 3 == 1) charactersForKeys(keyPressed.asDigit.toInt).take(1)
//      else charactersForKeys(keyPressed.asDigit.toInt).drop(1).take(1)
//      
//    }
//  }

  def charFor(keyPressed: Char, timesPressed: Int) = {
    val vaihtoehdot = charactersForKeys(keyPressed.asDigit.toInt)
    val monesko = (timesPressed - 1) % vaihtoehdot.size 
    val palauta = vaihtoehdot(monesko)
    palauta
  }
  
  /**
   * Determines the first letter of the given string, and returns the number of times
   * the letter occurs consecutively at the beginning of the string. The given
   * string must have at least one character.
   */
  private def countInitialCopies(source: String) = {
    val first = source(0) // temporary
    var count = 1 // stepper
    while (count < source.length && source(count) == first) {
      count += 1
    }
    count
  }

  /**
   * Returns the text that is produced by this keypad from the given multi-tap input.
   * For instance, the input `"5 5553330000"` produces `"MOI!"`, if '5' corresponds to
   * "MNO"; '3' corresponds to "GHI", and '0' corresponds to " .,!?". The given string
   * is assumed to consist of digits and spaces only.
   */
  def toText(keysPressed: String) = {
    val pieces = keysPressed.split(" ")
    var result = "" // gatherer
    for (currentPiece <- pieces) { // most-recent holder
      var remaining = currentPiece // gatherer 
      while (remaining.nonEmpty) {
        val copyCount = this.countInitialCopies(remaining) // temporary
        result += this.charFor(remaining(0), copyCount)
        remaining = remaining.drop(copyCount)
      }
    }
    result
  }

  // Note to students: Consider alternative implementations for these
  // methods once you're familiar with higher-order functions (Chapters 7.3 to 8.1).

}
