package o1.football2

import scala.collection.mutable.Buffer

class Match(val home: Club, val away: Club) {

  private val homeScorers = Buffer[Player]()    // container: goalscorers of the home team are added here
  private val awayScorers = Buffer[Player]()    // container: goalscorers of the away team are added here

  
  def winner = {  
    if (this.goalDifference < 0)
      Some(this.away)
    else if (this.goalDifference > 0)
      Some(this.home)
    else  
      None 
  }


  def addGoal(scorer: Player): Unit = {
    if (scorer.employer == this.home) {
      this.homeScorers += scorer
    } 
    else this.awayScorers += scorer
  }
  
  def homeGoals = this.homeScorers.size  /** Returns the number of goals that have been scored (so far) by the home team. */
 
  def awayGoals = this.awayScorers.size  /** Returns the number of goals that have been scored (so far) by the away team. */
 
  def totalGoals = this.homeGoals + this.awayGoals  /** Returns the total number of goals scored by the two teams. */
 
  def isHomeWin = this.homeGoals > this.awayGoals /** Returns a boolean value indicating whether the home team won (or would win if the match ended with the current score). Tied scores produce `false`. */
   
  def isAwayWin = this.homeGoals < this.awayGoals
   
  def winningGoalScorer = {
    if (isHomeWin) Some(homeScorers(this.awayScorers.size))
    
    else if (isAwayWin) Some(awayScorers(this.homeScorers.size))
    
    else None
  }
 
  
  def isTied = this.homeScorers.size == this.awayScorers.size    /** Returns a boolean value indicating whether the game ended in a draw (or would do so if the match ended with the current score). */

  /** Returns the goal difference of the match. The sign of the number indicates 
    * which team scored more goals.
    *
    * @return the goal difference as a positive number if the home team won, a 
    *         negative number if the away team won, or zero in case of a tied match  
    */
  def goalDifference = this.homeGoals - this.awayGoals
   
   
  /** Determines whether this match has a higher total score than another given match.
    *
    * @return `true` if more goals were scored in total in this match than in the given match, `false` otherwise 
    */
  def isHigherScoringThan(anotherMatch: Match) = this.totalGoals > anotherMatch.totalGoals
   
   
  /** Determines whether this match is entirely goalless, that is, whether neither
    * team has scored a single goal. */
  def isGoalless = this.totalGoals == 0
   
   
  /** Returns the name of the stadium where the match is played. That is, returns 
    * the name of the home team's stadium. */
  def location = this.home.stadium
   
   
  /** Produces a textual description of the current state of the match, in a format 
    * illustrated by the examples below. In these examples "Liverpool" is the name 
    * of the home team, "Everton" the name of the away team, and "Anfield" the name 
    * of the home team's stadium.
    *
    * Goalless draw (no goals for either side):
    * {{{
    * Liverpool vs. Everton at Anfield: tied at nil-nil 
    * }}}
    *
    * Other tied score (e.g., 2-2):
    * {{{
    * Liverpool vs. Everton at Anfield: tied at 2-all 
    * }}}
    *
    * Home score higher (e.g., 4-0): 
    * {{{
    * Liverpool vs. Everton at Anfield: 4-0 to Liverpool
    * }}}
    *
    * Away score higher (e.g., 2-4): 
    * {{{
    * Liverpool vs. Everton at Anfield: 4-2 to Everton 
    * }}}
    *
    * Note that although the name of the home team is always listed first, but the 
    * leader's score is shown first, even if the away team is leading.
    */
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

  
  
