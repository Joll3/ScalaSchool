package kierros3

/**
 * Represents an image that consistst of RGBA pixels.
 * 
 * @author ankkukku
 * 
 * @param data The image's pixel data as a two-dimensional `scala.Vector`
 *             containing `scala.Int` objects.
 */
class Image(val data: Vector[Vector[Int]]) {

  // getters and setters for the width and height of the image
  private val _height: Int = data.length
  def height: Int = _height
  private val _width: Int = data(0).length
  def width: Int = _width
  
  // is used to store the modified color data for each pixel as Int
  private var modifiedData: Vector[Vector[Int]] = _
  
  /**
   * Returns the image data in usable form.
   *
   * @return The image as two-dimensional `scala.Vector` containing
   *         `scala.Int` objects. If image has been modified the modified
   *         data is returned.
   */
  def getImage() : Vector[Vector[Int]] = {
    if (modifiedData != null) {
      modifiedData
    } else {
      data
    }
  }
  
  /**
   * Sets the given two dimensional vector `pixels` to the variable for modified
   * image data.
   * 
   * This method should be called after a filter is operated on image.
   *
   * @param pixels The new modified data for image.
   */
  def setImage(pixels: Vector[Vector[Color]]): Unit =
    modifiedData = pixels.map(x => x.map(x => x.rgba))

  /**
   * Returns the value for the original red component for pixel at (`x`, `y`).
   *
   * @param x The x coordinate of the pixel whose color data is needed.
   * @param y The y coordinate of the pixel whose color data is needed.
   * @return  The value of the red component for pixel at (`x`, `y`).
   */
  def getR(x: Int, y: Int) : Int = getColor(x, y).r

  /**
   * Returns the value for the original green component for pixel at (`x`, `y`).
   *
   * @param x The x coordinate of the pixel whose color data is needed.
   * @param y The y coordinate of the pixel whose color data is needed.
   * @return  The value of the green component for pixel at (`x`, `y`).
   */
  def getG(x: Int, y: Int) : Int = getColor(x, y).g

  /**
   * Returns the value for the original blue component for pixel at (`x`, `y`).
   *
   * @param x The x coordinate of the pixel whose color data is needed.
   * @param y The y coordinate of the pixel whose color data is needed.
   * @return  The value of the blue component for pixel at (`x`, `y`).
   */
  def getB(x: Int, y: Int) : Int = getColor(x, y).b
  
  /**
   * Returns the value for the original alpha component for pixel at (`x`, `y`).
   *
   * @param x The x coordinate of the pixel whose color data is needed.
   * @param y The y coordinate of the pixel whose color data is needed.
   * @return  The alpha value for pixel at (`x`, `y`).
   */
  def getA(x: Int, y: Int) : Int = getColor(x, y).a

  /**
   * Returns the value for the original color component as `scala.Int`;
   * for pixel at (`x`, `y`).
   *
   * @param x The x coordinate of the pixel whose color data is needed.
   * @param y The y coordinate of the pixel whose color data is needed.
   * @return  The color data as `Int`, each component takes 8 bits (value range
   *          is 0-255).
   */
  def getColor(x: Int, y:Int) : Color = new Color(data(x)(y))

}
