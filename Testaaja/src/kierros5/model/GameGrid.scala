package kierros5.model

import java.awt.Color
import scala.collection.mutable.ArrayBuffer


/**
 * @author 424796 and 429254
 */

// A class for the gameboard and its mechanics, size parameterized
class GameGrid(val height: Int, val width: Int) {
  
  // Fill the two-dimensional gameboard array with empty blocks
  private val board: ArrayBuffer[ArrayBuffer[Shape]] = ArrayBuffer.fill(this.height, this.width)(` `)
  
  // Define where new pieces will start at when falling
  private val offset = this.width/2 - 2
  
  // Variables for the current and next pieces
  private var falling: Piece = new Piece(this.offset, 0, Shape.getRandom(), this)
  private var next:    Piece = new Piece(this.offset, 0, Shape.getRandom(), this)
  
  private var ended = false // One-time flag
  
  // Determines whether the game is still playable
  def hasEnded = this.ended
  
  // End the game
  def endGame() = this.ended = true
  
  private var clearedRows = 0     // The amount of rows cleared
  private var scoreCount: Long = 0// The score obtained
  private var currentLevel = 0
  private val levelIncrement = 15 // How many rows have to be cleared for every levelup
  
  // Method to increment the current level. Call nextLevel(0) to merely get the current level
  def nextLevel(increment: Int) = {
    val newLevel = this.currentLevel + increment
    this.currentLevel = newLevel
    newLevel
  }
  
  // Method to increase the current score by parameter points. Call score(0) to merely get the current score
  def score(points: Int): Int = {
    var newScore = this.scoreCount.toInt + (currentLevel + 1) * points * 100
    this.scoreCount = newScore.toLong
    newScore
  }
  
  def getFallingPiece() : Piece = this.falling
  def getNextPiece() : Piece = this.next
  
  // Method returning the shape at set coordinates
  def getShape(x: Int, y: Int) : Shape = this.board(y)(x)
  
  // Method to set a set block to a certain shape
  def setShape(x: Int, y: Int, shape: Shape) : Boolean = {
    if (this.board(y)(x).getColor == Color.BLACK) {
      this.board(y)(x) = shape
      true
    }
    else false
  }
  
  // Method to set a set block to an empty shape
  def removeShape(x: Int, y: Int, shape: Shape) : Boolean = {
    if (this.board(y)(x).getColor == shape.getColor) {
      this.board(y)(x) = ` `
      true
    }
    else false
  }
  
  // The text (eg. score) to flash on the game panel
  var flash = ""
  
  // Method to remove any full horizontal lines built
  private def removeLines() {
    
    this.flash = "" // Reset the flash
    
    val fullRows = this.board.filter(_.forall(_.getColor != Color.BLACK)).length // Amount of full rows
    val oldScore = this.score(0)
    this.score(fullRows * fullRows) // Score!
    
    if (fullRows >= 4)
      this.flash = "Tetris!"
    else if(fullRows >= 1)
      this.flash = "+" + (this.score(0) - oldScore) // Display the obtained score
    
    if (fullRows > 0 && this.clearedRows % this.levelIncrement == 0)
      this.nextLevel(1) // Level-up
    
    // Analyze every row
    for (y <- 0 until this.height) {
      if (this.board(y).forall(_.getColor != Color.BLACK)) { // Full row!
        this.board -= this.board(y) // Remove it.
        
        // Add an empty row to complement the removal
        val emptyRow: ArrayBuffer[Shape] = ArrayBuffer.fill(this.width)(` `)
        this.board.prepend(emptyRow)
        
        this.clearedRows += 1
      }
    }
    
  }
  
  /* Method to start dropping the next piece in queue */
  def dropNextPiece() : Unit = {
    
    this.falling.drop() // Failsafe
    this.falling.addToGameGrid()
    
    this.falling = this.next
    this.removeLines()
    
    if (okToMove(this.next.getShapeData, this.offset, 0, 0, 0)) { // Make sure that a new piece fits at the top
      this.next = new Piece(this.offset, 0, Shape.getRandom(), this)
    }
    else { // End the game if it doesn't fit.
      this.next = null
      this.endGame()
    }
  }
  
  /** Method that checks if a piece fits into the gameboard.
   * @param shapeData The piece's two-dimensional shape data
   * @param x0        The x-coordinate of the piece
   * @param y0        The y-coordinate of the piece
   * @param xChange   The proposed shift in the piece's x-coordinate
   * @param yChange   The proposed shift in the piece's y-coordinate
   * */
  def okToMove(shapeData: List[List[Boolean]], x0: Int, y0: Int, xChange: Int, yChange: Int): Boolean = {
    var ok = true
    
    for (y <- 0 until shapeData.length) {
      for (x <- 0 until shapeData(y).length) {
        if (shapeData(y)(x)) { // Is the piece interested in this location?
          val newX = x0 + x + xChange
          val newY = y0 + y + yChange
          try {
            if (this.board(newY)(newX).getColor != Color.BLACK) // Is the block empty/available?
              throw new IllegalArgumentException
          } catch {
            case _: Throwable => ok = false // The shift would cause a collision OR lead outside of the game area 
          }
        }
      }
    }
    ok
  }
      
  override def toString = "Height: " + this.height + ", width: " + this.width
}