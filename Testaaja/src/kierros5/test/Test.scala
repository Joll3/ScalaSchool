package kierros5.test
import kierros5.model._
import org.scalatest._
import java.awt.Color

class Test extends FlatSpec with Matchers {
  
  "A Piece" should "add itself to the game grid when requested" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = new Piece(0, 0, shape, board)
    
    piece.addToGameGrid()
    
    val coords = Vector((2, 0), (2, 1), (2, 2),(2, 3))
    var ok = true
    
    for (i <- coords)
      if (board.getShape(i._1, i._2).getColor != piece.shape.getColor)
        ok = false
    
    ok should be (true)
  }
  
  it should "rotate when the direction call is up" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = new Piece(0, 0, shape, board)
    val rotated = new Piece(0, 0, shape, board).getShapeData.transpose.reverse
    
    piece.move(Direction.up)
    
    var ok = true
    
    if (piece.getShapeData != rotated)
      ok = false
    
    ok should be (true)
  }
  
  it should "move when the direction call is not 'up'" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = new Piece(0, 0, shape, board)
    val x = piece.getX + 1
    val y = piece.getY + 1
    
    piece.move(Direction.down)
    piece.move(Direction.right)
    
    var ok = true
    
    if (piece.getX != x || piece.getY != y)
      ok = false
    
    ok should be (true)
  }
  
  it should "not move when there isn't space" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = new Piece(0, 0, shape, board)
    val x = piece.getX - 1
    val y = piece.getY - 1
    
    piece.move(Direction.up)
    piece.move(Direction.left)
    
    var ok = true
    
    if (piece.getX != x || piece.getY != y)
      ok = false
    
    ok should be (false)
  }
  
  "A GameGrid" should "let a piece move if it has space to do so" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = new Piece(0, 0, shape, board)
    
    val ok = board.okToMove(shape.getShape(), 0, 0, 1, 1)
    
    ok should be (true)
  }
  
  it should "not let a piece move if it doesn't have space to do so" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = new Piece(0, 0, shape, board)
    
    val ok = board.okToMove(shape.getShape(), 0, 0, -2, -2)
    val ok2 = board.okToMove(shape.getShape(), 0, 0, 5, 5)
    
    ok  should be (false)
    ok2 should be (false)
  }
  
  it should " drop the current piece and change the falling piece when asked to" in {
    
    val board = new GameGrid(5, 5)
    val shape = I
    val piece = board.getFallingPiece
    
    board.dropNextPiece()
    val changed = board.getFallingPiece() != piece
    
    var floorHasShapes = false
    
    // Check for non-black blocks on the bottom row
    for (i <- 0 until 5) {
      floorHasShapes = board.getShape(i, 4).getColor != Color.BLACK || floorHasShapes
    }
    
    changed should be (true)
    floorHasShapes should be (true)
    
  }
  
  it should " remove full rows" in {
    
    val board = new GameGrid(10, 5)
    val oldScore = board.score(0)
    
    
    // Fill the two bottom rows
    for (i <- 0 until 5) {
      board.setShape(i, 9, I)
      board.setShape(i, 8, I)
    }
    
    board.dropNextPiece()
    
    var floorNotFull = false
    
    // Make sure that bottom row is not full
    for (i <- 0 until 5) {
      floorNotFull = board.getShape(i, 9).getColor == Color.BLACK || floorNotFull
    }
    
    val scoreIncreased = oldScore < board.score(0)
    
    floorNotFull should be (true)
    scoreIncreased should be (true)
    
  }
  
}