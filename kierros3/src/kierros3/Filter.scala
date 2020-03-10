package kierros3

/**
 * @author put your student number(s) here
 */

import scala.math._
import scala.collection.mutable.ArrayBuffer
object Filter {

  /* Filters the image's lightness based on parameter "factor" [0-20], iterating through each pixel */
  def lightness(factor: Int, img: Image) : Unit = {
    var newImage = Vector[Vector[Color]]()
    val coefficient = factor / 10.0
    
    for (x <- 0 until img.height) { 
      var row = Vector[Color]()
      for (y <- 0 until img.width) {
        
        // Change lightness for each pixel
        val red   = round(img.getR(x, y) * coefficient).toInt
        val green = round(img.getG(x, y) * coefficient).toInt
        val blue  = round(img.getB(x, y) * coefficient).toInt
        
        row = row :+ new Color(red, green, blue, img.getA(x, y))
      }
      newImage = newImage :+ row
    }
    img.setImage(newImage)
    
  }


  /* Filters the image to an inverted version, iterating through each pixel */
  def invert(img: Image) : Unit = {
    var newImage = Vector[Vector[Color]]()
    
    for (x <- 0 until img.height) { 
      var row = Vector[Color]()
      for (y <- 0 until img.width) {
        row = row :+ new Color(255 - img.getR(x, y), 255 - img.getG(x, y), 255 - img.getB(x, y), img.getA(x, y))
      }
      newImage = newImage :+ row
    }
    img.setImage(newImage)
    
  }

  /* Filters the image to a grayscale version, iterating through each pixel */
  def grayscale(img: Image) : Unit = {
    var newImage = Vector[Vector[Color]]()
    
    for (x <- 0 until img.height) { 
      var row = Vector[Color]()
      for (y <- 0 until img.width) {
        val gray = (img.getR(x, y) + img.getG(x, y) + img.getB(x, y)) / 3 // Calculate average color value
        row = row :+ new Color(gray, gray, gray, img.getA(x, y))
      }
      newImage = newImage :+ row
    }
    img.setImage(newImage)
  }


  def adjustRed(amount: Int, img: Image) : Unit = {
    var newImage = Vector[Vector[Color]]()
    
    for (x <- 0 until img.height) { 
      var row = Vector[Color]()
      for (y <- 0 until img.width) {
        row = row :+ new Color(img.getR(x, y) + amount, img.getG(x, y), img.getB(x, y), img.getA(x, y))
      }
      newImage = newImage :+ row
    }
    img.setImage(newImage)
  }

  
  def adjustGreen(amount: Int, img: Image) : Unit = {
    var newImage = Vector[Vector[Color]]()
    
    for (x <- 0 until img.height) { 
      var row = Vector[Color]()
      for (y <- 0 until img.width) {
        row = row :+ new Color(img.getR(x, y), img.getG(x, y) + amount, img.getB(x, y), img.getA(x, y))
      }
      newImage = newImage :+ row
    }
    img.setImage(newImage)
    
  }

  
  def adjustBlue(amount: Int, img: Image) : Unit = {
    var newImage = Vector[Vector[Color]]()
    
    for (x <- 0 until img.height) { 
      var row = Vector[Color]()
      for (y <- 0 until img.width) {
        row = row :+ new Color(img.getR(x, y), img.getG(x, y), img.getB(x, y) + amount, img.getA(x, y))
      }
      newImage = newImage :+ row
    }
    img.setImage(newImage)
    
  }


  def blur(amount: Int, image: Image) : Unit = {
    
  }


  def sharpen(amount: Int, image: Image) : Unit = {
    
  }
  
  /* Reports whether the cell should affect the filtration (by seed)  */
  private def colFiltration(amount: Int, seed:Float, x: Int, y: Int) = {
    val xValue = min(x, abs(amount - x)) // Mirror the x number for calculation eg. 0,1,2,3,4 to 0,1,2,1,0
    val yValue = min(y, abs(amount - y)) // Mirror the y number for calculation eg. 0,1,2,3,4 to 0,1,2,1,0

    val centerCell = amount / 2
    if (yValue >= (centerCell - xValue)) seed else 0 // If we're not at the corner, return seed.
  }
  
  /* Compiles a filter of size & type "amount", weighted by seed */
  private def getFilter(amount: Int, seed: Float) : Array[Array[Float]] = {
    var filter = Array[Array[Float]]()
    val center = 5
    if (amount % 2 == 1)
      filter = Array.fill(amount, amount)(seed)
    else
      filter = Array.tabulate(amount+1, amount+1)(colFiltration(amount, seed, _, _)) // Check the column's filtration
      
    filter
  }
  
  /* Multiplies the given pixel with value given by get filter method */
  private def multiplyWithFilter(x: Int, y: Int, image: Image, filter: Array[Array[Float]]) : Color = {
    
    val center = filter.length/2
    val x0 = x - center
    val y0 = y - center
    
    // If the filtration area intersects outside of the image, return the original color.
    if (x0 < 0 | y0 < 0 | (x0 + filter.length - 1) >= image.width | (y0 + filter.length - 1) >= image.height)
      return image.getColor(x, y)
    
    var r = 0.0
    var g = 0.0
    var b = 0.0
    
    // Sum up all the colors projected by the filter
    for (i <- 0 until filter.length) {
      for (j <- 0 until filter.length) {
        r += image.getR(x0 + i, y0 + j) * filter(i)(j)
        g += image.getR(x0 + i, y0 + j) * filter(i)(j)
        b += image.getR(x0 + i, y0 + j) * filter(i)(j)
      }
    }
    
    new Color(round(r).toInt, round(g).toInt, round(b).toInt, image.getA(x,y))
  }


  /**
   * Method for requesting filters without parameters. Called from the UI class.
   * Filters without parameter have a button in UI.
   * 
   * The returned vector contains Tuple2's that have a following structure:
   * (effect method pointer, effect's name).
   *  
   * @return Vector containing Tuple2s, each tuple contains the pointer to the
   *         effect method, and the name to display on a button in the UI 
   */
  def getFiltersWithoutParameter() : Vector[((Image) => Unit, String)] = {
    Vector((invert, "As Inverted"),
           (grayscale, "As Grayscale"))
  }


  /**
   * Method for requesting filters with parameter. Called from the UI class.
   * Filters with parameter have a slider in UI for adjusting the parameter
   * value. 
   * 
   * The returned vector contains Tuple5's that have a following structure:
   * (effect method pointer, effect's name, start value, min value, max value).
   * 
   * @return Vector containing Tuple5s, each tuple contains the pointer to the
   *         effect method, the name of the effect to display next to the
   *         slider, slider's start value, slider's minimum value, and slider's
   *         maximum value 
   */
  def getFiltersWithParameter() : Vector[((Int, Image) => Unit, String, Int, Int, Int)] = {
    Vector((lightness, "Adjust lightness", 10, 0, 20),
           (adjustRed, "Adjust Red", 0, -100, 100),
           (adjustGreen, "Adjust Green", 0, -100, 100),
           (adjustBlue, "Adjust Blue", 0, -100, 100),
           (blur, "Blur", 0, 0, 10),
           (sharpen, "Sharpen", 0, 0, 10))
  }

}
