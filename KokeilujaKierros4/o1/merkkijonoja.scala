

package o1
object merkkijonoja {
  
  import scala.collection.mutable.Buffer

  // Kirjoita luvun 4.4 tehtävissä pyydetty koodi tähän alle.
  

  def yhtaikaa(jono: Buffer[String], luku: Int) = jono.mkString("&") + "/" + luku
  
  def tempo(merkit: String) = {
    val aanet = merkit.split("/")
    if (aanet.size == 1) 120 else aanet(1).toInt
  }

}  
  
  
