package auction.log
import auction.event._

/**
 * Provided more as a debugging aid, the ConsolePrinter can be registered as a
 * Listener to a an auction event dispatcher.
 * 
 * It demonstrates how the match-structure can be used to extract information
 * from the matched items provided that they are either case-classes/case-objects
 * or classes with the an implementation of the unapply method. 
 */
object ConsolePrinter extends Listener {

  def receiveEvent(e: AuctionEvent) = {
    e match {
      case BidBeaten(bid)       => { println(s"BidBeaten      : ${bid.who}'s bid for ${bid.item.getName} is about to be beaten.") }
      case BidRaised(bid)       => { println(s"BidRaised      : ${bid.who} raised the price of ${bid.item.getName} to ${bid.price}.") }
      case AuctionExpired(item) => { println(s"AuctionExpired : Auction for ${item.getName} expired without bids.") }
      case AuctionWon(item)     => { println(s"AuctionWon     : ${item.getName} was sold for ${item.currentBid.get.price} to ${item.currentBid.get.who}.") }
      case _                    => { println(s"Unknown        : $e") }
    }
  }
}