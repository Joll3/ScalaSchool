package kierros5.gui
import _root_.kierros5.model._
import scala.swing._
import java.awt.event._
import javax.swing.Timer
import javax.swing.AbstractAction
import javax.swing.border._


/**
 * @author 424796 and 429254
 * */

/* A class for the visible interface */
object View extends SimpleSwingApplication {
  
  val blockSize = 25 // The size of one block (pixels) in the game grid
  
  var panel = new GamePanel(new GameGrid(22, 10))
  
  // Set the game grid size to match the amount of blocks visible
  this.panel.preferredSize = new Dimension(panel.game.width * View.blockSize, (panel.game.height - 2) * View.blockSize)
  
  private val windowColor = new Color(208, 241, 224) // General background color
  
  // Create the different UI elements
  
  private val window = new MainFrame
  this.window.resizable = false
  
  private val scoreCounter = new Label("Score: 0")
  this.scoreCounter.border = new EmptyBorder(10, 10, 10, 10) // Some padding
  
  private val nextPiece = new PiecePanel
  this.nextPiece.background = windowColor
  this.nextPiece.updatePiece(View.panel.game.getNextPiece)
  
  private val nothing = new Label("") // Required for BorderPanel to comply
  this.nothing.background = windowColor
  
  private val sideBar = new BorderPanel {
    import BorderPanel.Position._
    layout(nextPiece)   = Center
    layout(nothing) = East
    layout(scoreCounter) = North
  }
  this.sideBar.preferredSize = new Dimension(5 * View.blockSize, (panel.game.height - 2)  * View.blockSize)
  this.sideBar.background = windowColor
  
  private val row = new BorderPanel {
    import BorderPanel.Position._
    layout(panel)   = Center
    layout(sideBar) = East
    layout(nothing) = South
  }
  
  window.contents = row
  panel.requestFocus()
  window.title = "Tetris"
  
  // Set the timer variables
  private var dropRate = 500  // Time to wait before dropping the piece by one block (gravity)
  private val frameRate = 50  // Time to wait between each repaint      
  private var ticked = 0      // Counter to keep track of passed frames/time
  
  // Method to adjust the dropRate based on the level (make it faster as the game goes on)
  private def adjustRate() {
    this.dropRate = Math.max(100, 500 - 50*this.panel.game.nextLevel(0))
  }
  
  // Method returning which half of the dropRate tick we are on (required for blinking effect)
  def tick = this.ticked/(this.dropRate / 2)
  
  private var paused = false;
  def isPaused = this.paused
  
  // Initialize the game timer
  val timer = new Timer(frameRate, new AbstractAction() {
      def actionPerformed(e : ActionEvent) {
        
        val game = View.panel.game
        
        View.ticked = (View.ticked + View.frameRate) % View.dropRate
        
        View.window.title = "Tetris - Level " + View.panel.game.nextLevel(0)
        View.scoreCounter.text = "Score: " + View.panel.game.score(0)
        
        // If the game is still on and the falling piece can't move down
        if (!View.panel.game.hasEnded && View.ticked < View.frameRate && !game.getFallingPiece().move(Direction.down)) {
          game.dropNextPiece()
          // If that didn't end the game, update the "Next Piece" panel and adjust the drop rate
          if (!View.panel.game.hasEnded) {
            View.nextPiece.updatePiece(View.panel.game.getNextPiece)
            View.adjustRate()
          }
        }
        else if (View.panel.game.hasEnded)
          View.terminate() // The game ended; terminate the UI
          
        View.panel.repaint() // Refresh the view
      }
  })
  this.timer.start
  
  /* Stop the timer and display the ending message (called only after the game mechanic has ended itself) */
  def terminate(): Unit = {
    this.timer.stop()
    Dialog.showMessage(null, "You lost!\nSometimes the only winning move\nis refusing to play.", "Tetris")
  }
  
  /* Method to pause/unpause the game.*/
  def togglePause() = {
    if (this.isPaused) {
      this.paused = false
      this.timer.start
      this.nextPiece.repaint
    }
    else {
      this.paused = true
      this.timer.stop
      this.window.title = "[PAUSED] " + this.window.title
      this.panel.repaint
      this.nextPiece.repaint
    }
  }
  
  def top = this.window
}