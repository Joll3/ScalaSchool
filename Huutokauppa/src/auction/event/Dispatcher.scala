package auction.event
import scala.collection.mutable.Buffer

/**
 * Dispatcher takes care of dispatching an event to all the event listeners
 * interested in receiving one. The dispatcher is also a listener itself making
 * it possible to make a hierarchical event dispatching implementation.
 */
trait Dispatcher extends Listener {

  /**
   * Holds the list of listeners that should receive any events that are fired
   * or received by this dispatcher.
   */
  private val listeners = Buffer[Listener]()

  /**
   * Registers a new Listener for this dispatcher.
   */
  def registerListener(l: Listener): Unit = {
    listeners += l
  }

  /**
   * Removes an existing Listener from the list of listeners.
   */
  def removeListener(l: Listener): Unit = {
    listeners -= l
  }

  /**
   * Fires an AuctionEvent to be dispatched to all the listeners.
   */
  def fireEvent(e: AuctionEvent) = {
    listeners.foreach(_.receiveEvent(e))
  }

  /**
   * Receives an AuctionEvent (from another dispatcher) to be dispatched to all
   * the listeners of this dispatcher.
   */
  def receiveEvent(e: AuctionEvent) = fireEvent(e)

}