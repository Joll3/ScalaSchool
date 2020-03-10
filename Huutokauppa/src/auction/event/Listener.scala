package auction.event

/**
 * Describes the interface of any component that wants to receive and react to
 * events from the event dispatcher.
 */
trait Listener {

  /**
   * Receives an event from a dispatcher that this listener is registered to.
   *
   * @param e the event that occured
   */
  def receiveEvent(e: AuctionEvent)
}