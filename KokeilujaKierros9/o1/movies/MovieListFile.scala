package o1.movies

import scala.io.Source
import o1.util.useAndClose

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

class MovieListFile(val fileName: String) {
 
  val movies = this.parse(this.read())
 
  
  private def read() = {
    val movieFile = Source.fromURL(this.getClass.getResource(this.fileName), "UTF-8")
    useAndClose(movieFile)( _.getLines.toVector )   // The project O1Yleiset provides useAndClose, which is an implementation of the so-called Loan Pattern for using closeable resources such as files.
  }
    
  private def parse(listItems: Vector[String]): Vector[Movie] = {
    val tokenized = listItems.map( _.split(";") )
    tokenized.map( tokens => new Movie(tokens(0), tokens(1).toInt, tokens(2).toInt, tokens(3).toDouble, tokens(4).split(",").toVector) )
  } 
  
  override def toString = this.fileName + " (contains: " + this.movies.size + " movies)"
  
  
}