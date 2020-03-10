package o1.sananmuunnos

import org.scalatest._

class SanaSpec extends FlatSpec with Matchers {

  "prätkä, kontti" should "result in kotka präntti" in {
    val first = new Sana("prätkä")
    val second = new Sana("kontti")
    first.toString should be ("pr|ä|tkä")
    second.toString should be ("k|o|ntti")
    first.muunnos(second) should be ("kotka präntti")
  }

  "pastori, Luttinen" should "result in lustori pattinen" in {
    Sana.muunnos("pastori", "Luttinen") should be ("lustori pattinen")
  }

  "henri, kontinen" should "result in konri hentinen" in {
    Sana.muunnos("henri", "kontinen") should be ("konri hentinen")
  }

  "tarja, halonen" should "result in harja talonen" in {
    Sana.muunnos("tarja", "halonen") should be ("harja talonen")
  }

  "frakki, kontti" should "result in kokki frantti" in {
    Sana.muunnos("frakki", "kontti") should be ("kokki frantti")
  }

  "ovi, kello" should "result in kevi ollo" in {
    Sana.muunnos("ovi", "kello") should be ("kevi ollo")
  }

  "haamu, kontti" should "result in koomu hantti" in {
    Sana.muunnos("haamu", "kontti") should be ("koomu hantti")
  }

  "puoskari, kontti" should "result in kooskari puntti" in {
    Sana.muunnos("puoskari", "kontti") should be ("kooskari puntti")
  }

  "köyhä, kontti" should "result in kouha köntti" in {
    Sana.muunnos("köyhä", "kontti") should be ("kouha köntti")
  }

  "hauva, läähättää" should "result in läyvä haahattaa" in {
    Sana.muunnos("hauva", "läähättää") should be ("läyvä haahattaa")
  }

  "frakki, stressi" should "result in strekki frassi" in {
    Sana.muunnos("frakki", "stressi") should be ("strekki frassi")
  }

  "äyskäri, kontti" should "result in kouskari äntti" in {
    Sana.muunnos("äyskäri", "kontti") should be ("kouskari äntti")
  }

  "hattu, sfääri" should "result in sfätty haari" in {
    Sana.muunnos("hattu", "sfääri") should be ("sfätty haari")
  }

  "ovi, silmä" should "result in sivi olma" in {
    Sana.muunnos("ovi", "silmä") should be ("sivi olma")
  }

  "haamu, prätkä" should "result in präämy hatka" in {
    Sana.muunnos("haamu", "prätkä") should be ("präämy hatka")
  }

  "puoskari, sfääri" should "result in sfäöskäri puuri" in {
    Sana.muunnos("puoskari", "sfääri") should be ("sfäöskäri puuri")
  }

  "puoskari, äyskäri" should "result in äöskäri puuskari" in {
    Sana.muunnos("puoskari", "äyskäri") should be ("äöskäri puuskari")
  }
}