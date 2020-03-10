package o1.hiddenpics.gui

import scala.swing._
import javax.swing.UIManager
import javax.swing.ImageIcon



////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

/** The singleton object `HiddenPics` represents the Hidden Pics application, which
  * is intended for programming students to modify in order to see what is hidden
  * in some garbled image files. The object serves as an entry point for the application, 
  * and can be run to start up the user interface. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works 
  * or can be used, apart from the fact that you can use this file to start the program.''' 
  */
object HiddenPics extends SimpleSwingApplication {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 
  
  def top = new MainFrame {
    frame =>
    
    this.title = "Find out what's hiding in the pictures"
    this.location = new Point(100, 100)
    
    var currentPic: Option[HiddenPic] = None
    val picLabel = new Label {
      this.preferredSize = new Dimension(500, 300)
    }
    this.contents = picLabel
      
    this.menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Open hidden1.png") { update(new HiddenPic("/hidden1.png")) })
        contents += new MenuItem(Action("Open hidden2.png") { update(new HiddenPic("/hidden2.png")) })
        contents += new Separator
        contents += new MenuItem(Action("Quit") { dispose() })
      }
      contents += new Menu("Filter") {
        contents += new MenuItem(Action("Reveal 1") { for (pic <- frame.currentPic) { frame.update(pic.runFilter( _.filterReveal1 )) } })  
        contents += new MenuItem(Action("Reveal 2") { for (pic <- frame.currentPic) { frame.update(pic.runFilter( _.filterReveal2 )) } })
      }
    }
  
    def update(pic: HiddenPic) = {
      this.currentPic = Some(pic)
      val image = pic.image
      this.picLabel.icon = new ImageIcon(image)
      this.picLabel.preferredSize = new Dimension(image.getWidth, image.getHeight)
      this.pack()
    }
    
    
    
  }
  
  
  private class HiddenPic(val name: String) {
    import scala.math.{max, min}
    import javax.imageio.ImageIO
    import java.awt.image.BufferedImage
    import o1.hiddenpics._
    
    val pic = this.makePic()
  
    private def makePic() = {
      val source = ImageIO.read(this.getClass.getResource(name))
      val width = source.getWidth
      val height = source.getHeight
      val rawData = source.getRGB(0, 0, width, height, null, 0, width)
      val pixels = rawData.map(this.makePixel)
      new Picture(Array.tabulate(width, height)((x, y) => pixels(x + y * width)))
    }

    def runFilter(filter: Picture => Unit) = {
      filter(this.pic)
      this
    }

    private def makePixel(rgb: Int) = {
      val color = new Color(rgb)
      new Pixel(color.getRed, color.getGreen, color.getBlue)
    }

    private def makeColor(pixel: Pixel) = {
      val r = max(0, min(pixel.red, 255))
      val g = max(0, min(pixel.green, 255))
      val b = max(0, min(pixel.blue, 255))
      new Color(r, g, b).getRGB
    }

    private def makeImage(pic: Picture) = {
      val output = new BufferedImage(pic.width, pic.height, BufferedImage.TYPE_INT_RGB)
      for (i <- 0 until pic.width) {
        for (j <- 0 until pic.height) {
          output.setRGB(i, j, this.makeColor(pic(i, j)))
        }
      }
      output
    }

    def image = this.makeImage(this.pic)

  } 
  
}


// The first hidden pic is by Duane Wessels, published under CC:
// http://creativecommons.org/licenses/by-nc-sa/3.0/
// The second hidden pic is in the public domain.
//
// The HiddenPics program is in the public domain.