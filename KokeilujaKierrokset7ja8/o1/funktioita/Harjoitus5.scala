package o1.funktioita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 7.3.

object Harjoitus5 extends App {

  // TEHTÄVÄN VAIHE 1:
  // Toteuta koostaTulosAlkioista-funktio sellaiseksi, että se muodostaa tuloksen
  // soveltamalla kolmanneksi parametriksi annettua funktiota niihin lukuihin,
  // jotka ensimmäiseksi parametriksi annettu kokoelma sisältää. Toinen parametri
  // kertoo alkuarvon (joka palautetaan, mikäli lukuja ei ole lainkaan).
  //
  // Esimerkiksi jos "lukuja" sisältää luvut 20, 10 ja 5, "alkuarvo" on 0 ja
  // "operaatio" on funktio, joka palauttaa parametriensa summan, niin:
  //   1. Sovelletaan parametriksi saatua "operaatio"-funktiota ensin
  //      alkuarvoon ja ensimmäiseen lukuun. 0 + 20 on 20.
  //   2. Sovelletaan operaatiota sitten äskeiseen tulokseen ja seuraavaan
  //      kokoelman alkioon. 20 + 10 = 30.
  //   3. Sovelletaan operaatiota taas äskeiseen tulokseen ja seuraavaan alkioon.
  //      30 + 5 = 35.
  //   4. Viimeinenkin alkio on käsitelty, joten lopetetaan ja palautetaan 35.
  // Tämä esimerkki oli siis eräs tapa laskea alkioiden summa.
  
  def koostaTulosAlkioista(lukuja: Vector[Int], alkuarvo: Int, operaatio: (Int, Int) => Int) = {
    var alku = alkuarvo
    for (alkio <- 0 until (lukuja.size)){
      alku = operaatio(alku, lukuja(alkio)) // alku += lukuja(alkio)
    }
    alku
  }

  def laskeSumma(vanhaTulos: Int, seuraavaLuku: Int) = vanhaTulos + seuraavaLuku

  def laskePositiiviset(vanhaLuku: Int, seuraavaLuku: Int) = {
    var vastaus = vanhaLuku
    if (seuraavaLuku > 0) {
      vastaus += 1
    }
    vastaus
  }
   
    
  def laskeTulo(vanhaLuku: Int, seuraavaLuku: Int) = { if (seuraavaLuku == 0) vanhaLuku else (vanhaLuku * seuraavaLuku)
    
  }
 
  
  val esimerkkilukuja = Vector(100, 25, -12, 0, 50, 0)
  println("Luvut ovat: " + esimerkkilukuja.mkString(", "))

  println("Summa: " + koostaTulosAlkioista(esimerkkilukuja, 0, laskeSumma)) // pitäisi tulostaa: Summa: 163

  
  // TEHTÄVÄN VAIHE 2:
  // Käytä koostaTulosAlkioista-funktiota laskeaksesi:
  //   - montako positiivista lukua on esimerkkilukujen joukossa, ja
  //   - mikä on kaikkien esimerkkilukujen tulo (kuitenkin ohittaen nolla-alkiot!).
  // Nimeä funktiot näin: laskePositiiviset ja laskeTulo.
  // Korvaa alla olevat nollat sopivanlaisilla koostaTulosAlkioista-kutsuilla.
  val montakoPositiivista = koostaTulosAlkioista(esimerkkilukuja, 0, laskePositiiviset) // TODO: Toteuta sopiva funktio ja käytä sitä koostaTulosAlkioista-funktion parametrina.
  val lukujenTulo = koostaTulosAlkioista(esimerkkilukuja, 1, laskeTulo)         // TODO: Toteuta sopiva funktio ja käytä sitä koostaTulosAlkioista-funktion parametrina.
  println("Positiivisia: " + montakoPositiivista + " kpl") // pitäisi tulostaa: Positiivisia: 3 kpl
  println("Tulo: " + lukujenTulo)                          // pitäisi tulostaa: Tulo: -1500000

}
