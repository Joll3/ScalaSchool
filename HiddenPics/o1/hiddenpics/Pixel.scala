package o1.hiddenpics

/** This class represents picture elements, or pixels. Each pixel has a color 
  * which is characterized by three components: red, green and blue. These values 
  * range from 0 to 255.
  *
  * For example, a black pixel would be created like this: `new Pixel(0, 0, 0)`.
  * And a white pixel like this: `new Pixel(255, 255, 255)`.  
  *
  * A pixel object is immutable. 
  *
  * @param red    the value of the pixel's red component; must be between 0 and 255 (inclusive)
  * @param green  the value of the pixel's green component; must be between 0 and 255 (inclusive)
  * @param blue   the value of the pixel's blue component; must be between 0 and 255 (inclusive)
  */
class Pixel(val red: Int, val green: Int, val blue: Int) {
  
  /** Returns a textual representation of the pixel's R, G, and B components. */
  override def toString = "R: " + this.red + ", G:" + this.green + ", B:" + this.blue
  
}



