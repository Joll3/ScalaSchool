package o1.viinaharava

import o1.grid._
import scala.util.Random


/** Each instance of the class `GameBoard` represents a board for a game of Viinaharava.
  * A game board is a `Grid` whose elements are `Glass` objects. 
  *
  * Some of these glasses --- in random locations --- will be initialized to contain booze; 
  * the rest keep their default content of water. The glasses are created and filled
  * as part of creating a `GameBoard`. 
  *
  * @param width       the number of glasses in each row of the game board
  * @param height      the number of rows of glasses in the game board
  * @param boozeCount  the number of booze glasses on the game board. When a `GameBoard` object 
  *                    is created, it randomly chooses this many different locations on the board
  *                    and pours booze in them.
  *
  * @see [[o1.grid.Grid]] 
  * @see [[Glass.pourBooze]]
  */
class GameBoard(width: Int, height: Int, boozeCount: Int) extends Grid[Glass](width, height) {

  this.placeGlasses(boozeCount)   
  
  private def placeGlasses(boozeCount: Int) = {
//    val topLeft = new Coords(0, 0)                  // This is just an example of how to place a single glass...       
//    this.update(topLeft, new Glass(this, topLeft))  // ... in the top left corner. Replace this with your own code.
//    // TODO: Replace the two lines above with code that properly initializes the game board; see Chapter 7.2
    var x = 0
    var y = 0
    
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        this.update(new Coords(x,y), new Glass(this, new Coords(x,y)))
      }
    }
    val rand = new Random
    val sekoitettu = rand.shuffle(this.allElements.toBuffer)
    val sijainnit = sekoitettu.take(boozeCount)
    
    for (viinat <- sijainnit) {
      viinat.pourBooze()
    }
  }
  
  
  
  
  // Note to students: You don't need to (and shouldn't) modify the following methods.
  
  /** Returns a collection of all the booze glasses currently on the board. */
  def boozeGlasses = this.allElements.filter( _.isBooze ).toVector
  

  /** Determines whether all the water on the board has been drunk already. */ 
  def isOutOfWater = this.allElements.forall( glass => glass.isBooze || glass.isEmpty )

  
  
  
}

