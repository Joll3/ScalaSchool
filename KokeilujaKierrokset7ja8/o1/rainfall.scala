package o1

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 8.1.

object rainfall {

  // Kirjoita tähän alle funktio averageRainfall, joka toimii luvussa 8.1 kuvatulla tavalla. 
  
  def averageRainfall(mittausjaksot: Vector[Int]): Option[Int] = {
    val vastaus = mittausjaksot.take(mittausjaksot.indexOf(999999)).filter(_>=0)
    val palautus = if (vastaus.size >0) Option(vastaus.sum / vastaus.size) else None
    palautus
  }
  
}

//object rainFallTest extends App {
//  var testi = Vector(999999, 20, 30, 90, 999999, 0, 0, 999999)
//  println(testi)
//  var vastaus = testi.take(testi.indexOf(999999)).filter(_>=0)
//  println(vastaus)
//  if (vastaus.size >0) println(Option(vastaus.sum / vastaus.size)) else println(None)
//  
//}