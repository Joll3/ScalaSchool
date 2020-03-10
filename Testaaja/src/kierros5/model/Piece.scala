package kierros5.model

import java.awt.Color

/**
 * @author 424796 and 429254
 */

class Piece(private var x: Int, private var y: Int, val shape: Shape, private val masterGrid: GameGrid) {
  
  private var shapeData = shape.getShape
  def getShapeData = this.shapeData
  
  def getX = this.x
  def getY = this.y

  /* Method to move the piece into the gameboard */
  def addToGameGrid(): Unit = {
    for (y <- 0 until this.shapeData.length) {
      for (x <- 0 until shapeData(y).length) {
        if (this.shapeData(y)(x)) {
          val newX = this.x + x
          val newY = this.y + y
          this.masterGrid.setShape(newX, newY, this.shape)
        }
      }
    }
  } 
  
  
  /* Method to move the piece in a given direction*/
  def move(direction: Direction.Value) : Boolean = {
    
    if (direction == Direction.up) {
      val newData = this.shapeData.transpose.reverse // Rotate the piece
      
      // Make sure that the rotation is OK (ignore the RED cube, it's symmetrical)
      if (this.shape.getColor != Color.RED && this.masterGrid.okToMove(newData, this.x, this.y, 0, 0)) {
        this.shapeData = newData
        true
      }
      else false
    }
    else {
      val xChange = Direction.getXChange(direction)
      val yChange = Direction.getYChange(direction)
      
      if (this.masterGrid.okToMove(this.shapeData, this.x, this.y, xChange, yChange)) {
        this.x += xChange
        this.y += yChange
        true
      }
      else false
    }
  }
  
  /* A method to drop the piece all the way down */
  def drop() : Boolean = {
    while (this.masterGrid.okToMove(this.shapeData, this.x, this.y, 0, 1)) {
      this.move(Direction.down)
    }
    true
  }
}