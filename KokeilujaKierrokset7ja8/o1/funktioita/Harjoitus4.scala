package o1.funktioita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 7.3.


object Harjoitus4 extends App {

  // Muodostaa lyhennetyn version kaksiosaisesta nimestä.
  // Esim. Sauli Niinistö --> S. Niinistö
  def lyhennaNimi(merkkijono: String) = {
    val palat = merkkijono.split(" ")
    palat(0)(0) + ". " + palat(1)
  }

  def muutaKaikki(taulukko: Array[String], muutosfunktio: String => String) = {
    for (i <- 0 until taulukko.size){
      taulukko(i) = muutosfunktio(taulukko(i))
    }
  }
  
  // TODO: Toteuta tähän funktio muutaKaikki siten, että alla oleva
  // koodi toimii ja tuottaa kuvatunlaisen tulosteen.
  // muutaKaikki-funktion on siis otettava parametriksi taulukollinen
  // merkkijonoja sekä muutosfunktio, ja vaihdettava kaikki taulukon
  // alkiot toisiksi annettua funktiota käyttäen.


  val nimia = Array("Umberto Eco", "James Joyce", "Dorothy Dunnett")
  println(nimia.mkString(", "))        // tulostaa: Umberto Eco, James Joyce, Dorothy Dunnett
  muutaKaikki(nimia, lyhennaNimi)   // TODO: Poista kommenttimerkki rivin alusta.
  println(nimia.mkString(", "))        // pitäisi tulostaa: U. Eco, J. Joyce, D. Dunnett
  //println(lyhennaNimi(nimia(0)))        //oma
}
