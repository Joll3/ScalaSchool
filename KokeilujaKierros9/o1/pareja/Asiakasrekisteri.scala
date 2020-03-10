package o1.pareja

import scala.collection.mutable.Map

// TÄMÄ LUOKKA LIITTYY LUKUUN 9.1.

class Asiakasrekisteri(val nimi: String) {

  private val asiakkaat = Map[Int, Asiakas]() 

  def lisaa(uusiAsiakas: Asiakas) = {
    this.asiakkaat(uusiAsiakas.numero) = uusiAsiakas
  }

  def hae(asiakasnumero: Int): Option[Asiakas] = this.asiakkaat.get(asiakasnumero)
  
  def kuolinvuosi(asiakasnumero: Int): Option[Int] ={ 
    val vastaus = this.asiakkaat.get(asiakasnumero)
    vastaus.flatMap(_.kuollut)
    }
  
}
