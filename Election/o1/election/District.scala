package o1.election
import scala.collection.mutable.Buffer
/**
 * @author Joll4
 */
class District(val name: String, val seats: Int, val candidates: Vector[Candidate]) {
  
  private val ehdokasPuolueet = Buffer[String]()
  
  def candidatesFrom(party: String): Vector[Candidate] = {
    val persons = Buffer[Candidate]()
    for (person <- candidates) {
      if (person.party == party) persons += person
    }
    persons.toVector
  }
  
  def printCandidates(): Unit = {
    for (current <- this.candidates) {
      println(current.toString)
    }
  }
  
  def topCandidate: Candidate = {
    var most = candidates.head
    for (person <- candidates.tail) {
      if (person.votes > most.votes) most = person
    }
    most
  }
  
  private def countVotes(candidates: Vector[Candidate]) = {
    var summa = 0
    for (aanet <- candidates)
      summa += aanet.votes
      summa
  }
  
  def totalVotes(party: String): Int = {
    countVotes(candidatesFrom(party))
  }
  
  def totalVotes: Int = countVotes(candidates)
  
  def candidatesByParty: Map[String, Vector[Candidate]] = {
    for(ehdokas <- candidates){
      if(!ehdokasPuolueet.contains(ehdokas.party)) ehdokasPuolueet += ehdokas.party
    }
    val ehdokkaatPuolueittain = ehdokasPuolueet.map ( x => x -> this.candidatesFrom(x)).toMap
    ehdokkaatPuolueittain
    }
 
  
  def topCandidatesByParty: Map[String, Candidate] = {
    val aaniharavaPuolueittain = ehdokasPuolueet.map (x => x -> this.candidatesFrom(x).maxBy { x => x.votes }).toMap
    aaniharavaPuolueittain
  }
  
  def votesByParty: Map[String, Int] = {
    val aanetPuolueittain = ehdokasPuolueet.map (x => x -> this.totalVotes(x)).toMap
    aanetPuolueittain
  }
  
  def rankingsWithinParties: Map[String, Vector[Candidate]] = {
    candidatesByParty.mapValues(_.sortBy(_.votes).reverse)
  }
  
  def rankingOfParties: Vector[String] = {
    val aanet = votesByParty.toVector.sortBy(_._2).reverse.map(_._1)
    aanet
  }
  
  def distributionFigures: Map[Candidate, Double] = {
    def vertailuluku(hlo: Candidate):Double = this.totalVotes(hlo.party) / (rankingsWithinParties(hlo.party).indexOf(hlo) + 1)
    candidates.map (x => x -> this.totalVotes(x.party) / (rankingsWithinParties(x.party).indexOf(x) + 1).toDouble).toMap
  }
  
  def electedCandidates: Vector[Candidate] = {
    val luvunMukaan = distributionFigures.toVector.sortBy(_._2).reverse.take(seats)
    val hlot = luvunMukaan.map(_._1)
    hlot
  }
  
  override def toString: String = this.name + ": " + this.candidates.size + " candidates, " + this.seats + " seats"
  
}