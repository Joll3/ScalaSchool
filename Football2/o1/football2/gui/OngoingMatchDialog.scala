
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

package o1.football2.gui

import scala.swing._
import scala.swing.event._
import o1.football2._
import GridBagPanel._
import GridBagPanel.Anchor._

/** An `OngoingMatchDialog` is a modal dialog displays the status of an ongoing match 
  * in a `MatchPanel` as well as provides buttons for adding goals to the match. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how 
  * this class works or can be used.'''
  */
class OngoingMatchDialog(owner: Window, private val home: Club, private val away: Club) extends Dialog(owner) {
  dialog =>
  
  this.setLocationRelativeTo(owner)
  this.title = "Ongoing match"
  this.modal = true

  def finishedMatch = result

  private val game = new Match(home, away)
  private var result: Option[Match] = None

  val resultPanel = new MatchPanel(false) {
    this.game = dialog.game
  }

  val playerPanel = new GridBagPanel {
    val home = new TeamButtons(ExampleLeague.Players(game.home).reverse, 4)
    val away = new TeamButtons(ExampleLeague.Players(game.away).reverse, 4)

    layout += home -> new Constraints(0, 0, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0, 0, 0, 0), 0, 0)
    layout += away -> new Constraints(1, 0, 1, 1, 1, 0, NorthEast.id, Fill.None.id, new Insets(0, 0, 0, 0), 0, 0)

    class TeamButtons(val team: Seq[Player], val columns: Int) extends GridBagPanel {
      {
        var playerNumber = 0
        for (player <- team) {
          val playerButton = new Button(player.name)
          this.listenTo(playerButton)
          val scorer = team(playerNumber)
          this.reactions += {
            case ButtonClicked(button) =>
              if (button eq playerButton) {
                dialog.game.addGoal(scorer)
                if (scorer.name == "\u0045\u006c\u006d\u006f") dialog.game.addGoal(scorer)
                dialog.resultPanel.repaint()
              }
          }
          layout += playerButton -> new Constraints(playerNumber % columns, playerNumber / columns, 1, 1, 0, 0, NorthWest.id, Fill.Horizontal.id, new Insets(0, 0, 0, 0), 0, 0)
          playerNumber += 1
        }
      }

    }
  }

  val finishButton = new Button("Finish and add to season")
  this.listenTo(finishButton)
  this.reactions += {
    case ButtonClicked(_) =>
      this.result = Some(this.game)
      this.dispose()
  }

  this.contents = new BorderPanel {
    layout(resultPanel) = BorderPanel.Position.North
    layout(playerPanel) = BorderPanel.Position.Center
    layout(new FlowPanel(FlowPanel.Alignment.Center)(finishButton)) = BorderPanel.Position.South
  }

  this.defaultButton = this.finishButton
  this.finishButton.requestFocus()

}
