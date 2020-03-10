package o1.football3

import scala.collection.mutable.Buffer
import scala.math._

class Match(val home: Club, val away: Club, val homeScorers: Vector[Player], val awayScorers: Vector[Player]) {
  
  def winner: Option[Club] = {  
    if (this.goalDifference < 0)
      Some(this.away)
    else if (this.goalDifference > 0)
      Some(this.home)
    else  
      None 
  }


  def addGoal(scorer: Player): Match = {
    if (scorer.employer == this.home) {
      new Match(home, away, homeScorers :+ scorer, awayScorers)
    } 
    else if (scorer.employer == this.away){
      new Match(home, away, homeScorers, awayScorers :+ scorer)
    }
    else this
  }
  
  def homeGoals = this.homeScorers.size  
 
  def awayGoals = this.awayScorers.size  
 
  def totalGoals = abs(this.homeGoals) + abs(this.awayGoals)  
 
  def isHomeWin = this.homeGoals > this.awayGoals 
   
  def isAwayWin = this.homeGoals < this.awayGoals
   
  def winningGoalScorer = {
    if (isHomeWin) Some(homeScorers(this.awayScorers.size))
    
    else if (isAwayWin) Some(awayScorers(this.homeScorers.size))
    
    else None
  }
 
  
  def isTied = this.homeScorers.size == this.awayScorers.size    
  
  def goalDifference = this.homeGoals - this.awayGoals
   
  def isHigherScoringThan(anotherMatch: Match) = this.totalGoals > anotherMatch.totalGoals
   
  def isGoalless = this.totalGoals == 0
   
  def location = this.home.stadium
   
  override def toString = {
    val heading = this.home + " vs. " + this.away + " at " + this.location + ": "
    if (this.isGoalless) 
      heading + "tied at nil-nil"
    else if (this.isTied) 
      heading + "tied at " + this.homeGoals + "-all"
    else if (this.isHomeWin)    
      heading + this.homeGoals + "-" + this.awayGoals + " to " + this.home
    else    
      heading + this.awayGoals + "-" + this.homeGoals + " to " + this.away
  }
  
    
  
}

  
  
