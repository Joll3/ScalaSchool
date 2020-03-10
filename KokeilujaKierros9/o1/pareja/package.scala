package o1

// TÄMÄ KOODI LIITTYY LUKUUN 9.1.

package object pareja {

  // Tämä esimerkkifunktio käsitellään luvussa 9.1.
  def ekaEiIsompi(lukupari: (Int, Int)) = lukupari._1 <= lukupari._2
  
  def onJarjestyksessa(luvut: Vector[Int]): Boolean = luvut == luvut.sorted
 
    //luvut.forall(x => (x <= luvut(luvut.indexOf(x) + 1)))
    //luvut.forall(x => (luvut(luvut.indexOf(x) + 1) >= x))
    
  
  
  def minuuteiksiJaSekunneiksi(sekunnit: Int) = {
    (sekunnit/60, sekunnit % 60)
  }
  // Kirjoita pyydetty ohjelmakoodi tänne.

  
  
  
}