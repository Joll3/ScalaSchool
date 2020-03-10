package kierros3

import org.scalatest._

/**
 * Run this spec to test that your Color class and object work properly.
 * 
 * @author jsimonss
 */
class ColorSpec extends FlatSpec with Matchers {


  "A color" should "be created from integer components" in {
    new Color(255, 255, 255, 255)
  }

  it should "be created from floats" in {
    new Color(0.8f, 0.5f, 0.3f, 1.0f)
  }

  it should "be created from a single integer" in {
    new Color(0x8833ffff)
  }
  
  it should "clamp its input when using integer components out of bounds" in {
    val c = new Color(400, 100, -200, 256)
    c.r should be (255)
    c.g should be (100)
    c.b should be (0)
    c.a should be (255)
  }

  it should "clamp its input when using float components out of bounds" in {
    val c = new Color(2.0f, -354.0f, 256.0f, 9001.0f)
    c.r should be (2)
    c.g should be (0)
    c.b should be (255)
    c.a should be (255)
  }

  it should "round float inputs" in {
    val c = new Color(24.4f, 0.5f, 65.8f, 255.5f)
    c.r should be (24)
    c.g should be (1)
    c.b should be (66)
    c.a should be (255)
  }

  "The color components" should "be accessible individually" in {
    val c = new Color(100, 200, 120, 255)
    c.r should be (100)
    c.g should be (200)
    c.b should be (120)
    c.a should be (255)
  }

  they should "be accesible as a unit" in {
    val c = new Color(0x23, 0x88, 0x10, 0xff)
    c.rgba should be (0x238810ff)
  }

}
