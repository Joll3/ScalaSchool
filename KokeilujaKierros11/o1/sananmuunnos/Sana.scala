package o1.sananmuunnos
import scala.collection.mutable.Buffer

class Sana(merkkijono: String) {
  
  def muunnos(toinen: Sana): String = {
      val toinensana: String = toinen.toString().replace("|", "")
      
      val thissinuusialku = Vector(toinen.kon(toinensana), toinen.vok(toinensana)*this.vok(merkkijono).size)
      val toisenuusialku = Vector(this.kon(merkkijono), this.vok(merkkijono)*toinen.vok(toinensana).size)
      
      val uusithis: String = thissinuusialku.mkString + this.loput(merkkijono)
      val uusitoinen: String = toisenuusialku.mkString + toinen.loput(toinensana)
      
      val thisassimilioitu = for( kirjain <- this.loput(merkkijono)){
        var palauta = Buffer[Char]()
        var tama = assimiloi(kirjain, toinen.vok(toinensana).head)
        palauta += tama
        palauta.mkString
      }
  
      
      //val toinenassimilioitu = foreach (kirjain <- toinen.loput(toinensana))d{
        //var palauta = Buffer[Char]()
        //var tama = assimiloi(kirjain, this.vok(merkkijono).head)
        //palauta += tama
        //palauta.mkString
      //}

      toinensana // väärin mutta ei herjaa
  }
  
  private def kon(sana: String):String = {
    sana.takeWhile(onKonsonantti(_))
  }
  
   private def vok(sana: String): String = {
     var vokaali = sana.drop(this.kon(sana).length).head
     sana.dropWhile(onKonsonantti(_)).takeWhile{x => onVokaali(x)&& vokaali == x}
   }
  
  private def loput(sana: String): String = {
    sana.drop(this.kon(sana).length+this.vok(sana).length)
  } 
  
  private def eka(sana:String): String = {
    sana.take(1)
  }
  
  override def toString = Vector(kon(merkkijono), vok(merkkijono), loput(merkkijono)).mkString("|")
 
}


  
object Sana {

  def muunnos(ekaSana: String, tokaSana: String): String = this.muunnos(ekaSana, tokaSana) // TODO: toteuta myös tämä pikkumetodi
  
}
