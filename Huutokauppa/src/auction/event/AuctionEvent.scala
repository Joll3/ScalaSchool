package auction.event
import auction.{ Bid, AuctionItem, User }

/**
 * An AuctionEvent describes something that happens inside the auction.
 * Events are dispatched to interested parties in the program that can
 * decide themselves on how to react to them.
 */
trait AuctionEvent

/**
 * An event sent when a user successfully places a new highest bid.
 *
 * @param bid the new best bid.
 */
case class BidRaised(bid: Bid) extends AuctionEvent

/**
 * An event sent when a previous highest bid gets beaten.
 *
 * @param bid the beaten bid.
 */
case class BidBeaten(bid: Bid) extends AuctionEvent

/**
 * An event sent when an auction on an item closes without bids.
 *
 * @param item the expired item
 */
case class AuctionExpired(item: AuctionItem) extends AuctionEvent

/**
 * An event sent when an auction on an item closes and is won by someone.
 *
 * @param item the won item
 */
case class AuctionWon(item: AuctionItem) extends AuctionEvent

/**
 * An event sent when a new auction opens.
 *
 * @param item the opened item
 */
case class NewAuction(item: AuctionItem) extends AuctionEvent

/**
 * An event that requests for a message to be sent or logged.
 *
 * @param receiver the message recipient
 * @param message the message content
 */
case class MessagingEvent(receiver: User, message: String) extends AuctionEvent
