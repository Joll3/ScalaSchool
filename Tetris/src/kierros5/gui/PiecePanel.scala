package kierros5.gui
import _root_.kierros5.model._
import scala.swing._
import java.awt.Color

/**
 * @author 424796 and 429254
 */
/* A class for the "next piece" panel*/
class PiecePanel extends GridPanel(5, 5)  {
  
  private var model = List.fill(5, 5)(false)
  private var color = Color.BLACK
  
  def updatePiece(p: Piece) : Unit = {
    this.model = p.getShapeData
    this.color = p.shape.getColor
    this.repaint()
  }
  
  override def paintComponent(g:Graphics2D) : Unit = {
     super.paintComponent(g)
     // Draw a 5x5 panel containing the next piece
     for (y <- 0 until 5) {
       for (x <- 0 until 5) {
         if (this.model(y)(x) && !View.isPaused) {
           g.setPaint(color)
                  
              
           g.fillRect(x * View.blockSize, y * View.blockSize, View.blockSize, View.blockSize)
         
           g.setPaint(Color.gray)
           g.drawRect(x * View.blockSize, y * View.blockSize, View.blockSize, View.blockSize)
         }
       }
     }
  }
  
}