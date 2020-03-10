package kierros5.gui
import scala.swing._
import java.awt.Font
import java.awt.Color
import _root_.kierros5.model._
import scala.swing.event._


/**
 * @author 424796 and 429254
 */

/* A class for the game UX */
class GamePanel(val game: GameGrid) extends GridPanel(game.height, game.width) {
  
  override def paintComponent(g:Graphics2D) : Unit = {
     super.paintComponent(g)
     
     // Iterate through every visible block on the grid. Ignore the first two rows (they are hidden for a smoother drop)
     for (y <- 2 until this.game.height) {
       for (x <- 0 until this.game.width) {
         
         var color = this.game.getShape(x, y).getColor // Get the block's color
         
         // Draw the falling piece instead, if it's on our block
         try {
           val pieceX = x - this.game.getFallingPiece.getX
           val pieceY = y - this.game.getFallingPiece.getY
           
           if (this.game.getFallingPiece.getShapeData(pieceY)(pieceX) && !this.game.hasEnded)
             color = this.game.getFallingPiece.shape.getColor // Overwrite with the falling piece's color
         }
         catch {
           case _: Throwable => null
         }
           
         g.setPaint(color)
         if (View.isPaused)
            g.setPaint(Color.BLACK) // Hide the game board if we're paused (prevents cheating)
         g.fillRect(View.blockSize * x, View.blockSize * y - 2 * View.blockSize, View.blockSize, View.blockSize)
         
         // Flash the block borders based on the timer tick
         g.setPaint(new Color(50,50,50))
         if (View.tick%2 == 0)
           g.setPaint(new Color(40,40,40))
         
         g.drawRect(View.blockSize * x, View.blockSize * y - 2 * View.blockSize, View.blockSize, View.blockSize)
         
         // Flash the score balloon
         g.setPaint(Color.BLUE)
         if (View.tick%2 == 0)
           g.setPaint(Color.RED)
           
         g.setFont(new Font("Garamond", Font.BOLD, 32))
         g.drawString(this.game.flash, 10, 30);
       }
     }
  }
  
  this.listenTo(this.keys)
  
  private def movePiece(direction: Direction.Value, dropIt: Boolean) = {
    if (View.isPaused)
      View.togglePause() // Unpause the game
      
    if (!this.game.hasEnded)
      if (dropIt)
        this.game.getFallingPiece.drop()
      else
        this.game.getFallingPiece.move(direction)
  }
  
  this.reactions += {    
    case KeyPressed(_, Key.Up, _, _) =>
      this.movePiece(Direction.up, false)
      
    case KeyPressed(_, Key.Down, _, _) =>
      this.movePiece(Direction.down, false)
      
    case KeyPressed(_, Key.Space, _, _) =>
      this.movePiece(Direction.down, true)
      
    case KeyPressed(_, Key.Left, _, _) =>
      this.movePiece(Direction.left, false)
      
    case KeyPressed(_, Key.Right, _, _) =>
      this.movePiece(Direction.right, false)
      
    case KeyPressed(_, Key.P, _, _) =>
      View.togglePause()
  }
  
  this.focusable = true
  this.requestFocus()
  
  
}