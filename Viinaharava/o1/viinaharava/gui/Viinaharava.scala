package o1.viinaharava.gui

import scala.swing._
import o1.viinaharava._
import o1.grid.gui._
import javax.imageio.ImageIO
import o1.gui.Dialog._
import javax.swing.UIManager
import o1.grid.Coords

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////


/** The singleton object `Viinaharava` represents the Viinaharava application. The object 
  * serves as an entry point for the game, and can be run to start up the user interface. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works 
  * or can be used, apart from the fact that you can use this file to start the program.''' 
  */
object Viinaharava extends SimpleSwingApplication {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 
  UIManager.put("OptionPane.cancelButtonText", "Peruuta")
  
  
  def top = new MainFrame {
    this.title = "Viinaharava"
    this.location = new Point(50, 50) 
    this.menuBar = new MenuBar {
      contents += new Menu("Peli") {
        contents += new MenuItem(Action("Uusi 8 x 8") { startNewGame(createDefaultBoard) } )
        contents += new MenuItem(Action("Uusi...")    { requestBoard().foreach( startNewGame(_) ) } )
        contents += new Separator
        contents += new MenuItem(Action("Lopeta")     { dispose() } )
      }
    }  
    this.resizable = false
    this.startNewGame(this.createDefaultBoard)
   
    def createDefaultBoard = new GameBoard(8, 8, 12)  
    
    def requestBoard() = {
      def askWidth() =      requestInt("Pelilaudan leveys:",  _ > 0,  "Anna positiivinen kokonaisluku.", this.contents.headOption)
      def askHeight() =     requestInt("Pelilaudan korkeus:", _ > 0,  "Anna positiivinen kokonaisluku.", this.contents.headOption) 
      def askBoozeCount() = requestInt("Viinojen lukumäärä:", _ >= 0, "Anna ei-negatiivinen kokonaisluku.",  this.contents.headOption)
      for (width <- askWidth(); height <- askHeight(); boozes <- askBoozeCount()) yield new GameBoard(width, height, boozes) 
    }
    
    def startNewGame(board: GameBoard) = {
      val view = new ViinaharavaView(board)
      view.update()
      this.contents = new FlowPanel(view)
      this.pack()
    }

  }
    
  
  private class ViinaharavaView(val board: GameBoard) extends BasicGridView[GameBoard, Glass](board, ViinaharavaView.MaxSquareSize) { 
    import javax.imageio.ImageIO
    val RevealedBoozePic  = Array(this.scale(ImageIO.read(this.getClass.getResource("/pictures/empty.png"))))  
    val FullGlassPic      = Array(this.scale(ImageIO.read(this.getClass.getResource("/pictures/full.png" ))))
    val RevealedWaterPics = (for (danger <- 0 to 8) yield Array(this.scale(ImageIO.read(this.getClass.getResource("/pictures/" + danger + ".png"))))).toIndexedSeq
    val MissingPic        = RevealedWaterPics(0)
    
    def visualsFor(glass: Option[Glass]) = glass match {
      case Some(glass) if !glass.isEmpty => FullGlassPic
      case Some(glass) if glass.isBooze  => RevealedBoozePic
      case Some(glass)                   => RevealedWaterPics(glass.dangerLevel)
      case None                          => MissingPic
    }
    
    val popup = new Popup { 
      import PopupAction._
      this += new ElementAction("Juo",         AlwaysApplicable)( withNullCheck( drink(_) )(_) )
      this += new ElementAction("Lisää viina", AlwaysApplicable)( withNullCheck( _.pourBooze() )(_) )
    }

    private def withNullCheck(doStuff: Glass => Unit) = { // check for null to allow students to mess up more neatly
      def doOrComplain(glass: Glass) = Option(glass) match {   
        case Some(glass) => doStuff(glass)
        case None        => display("Virhe ohjelmassa: pelilautaa ei ole alustettu asianmukaisesti.", Some(this)) 
      } 
      doOrComplain _
    }
    
    def drink(glass: Glass) = {
      if (glass.drink()) {
        if (this.board.isOutOfWater) {
          this.update()
          display("Kaikki vedet kerätty!", Some(this))
        }
      } else {
        this.update()
        display("Osuit viinaan!", Some(this))
      }
    }
    
    def elementClicked(glass: Glass) = {
      withNullCheck(this.drink)(glass)
    } 

  }
  
  private object ViinaharavaView { 
    val MaxSquareSize = 100
  }
  
  
}  
  
