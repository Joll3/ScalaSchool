import auction.{ User, Auction, AuctionItem }
import auction.event._

package auction.bots {

  /**
   * A BidBot has one owner and one auction that it must listen. It can be set to
   * bid for multiple auction items for the user. The bot will make new bids
   * for an item in behalf of the user whenever another user makes a higher bit.
   * However, the bot will never bid more on an item than the maximum bid ceiling
   * set.
   */
  class BidBot(owner: User, auction: Auction with Dispatcher) extends Listener {

    /**
     * Adds a new auction item that the bot bids on.
     *
     * The bot stores all this information for each item somewhere.
     *
     * @param item an item to watch and bid for
     * @param bidCeiling a maximum amount to bid
     * @param raise an amount to raise from the last bid
     */
    def bidOnItem(item: AuctionItem, bidCeiling: Int, raise: Int) = ???

    /**
     * Receives events from the auction. The bot is especially interested in
     * BidRaised events.
     *
     * If anyone else than its owner makes a new best bid for a watched item
     * and the amount is less than the bidCeiling, the bot tries to make an
     * even better bid. It will bid a sum of the new best bid amount and the
     * raise if the sum is less than bidCeiling else the bot bids the
     * bidCeiling amount.
     *
     * The bot can only make a bid if its owner has credits. Every
     * bid made by the bot spends one credit. When and only when the last
     * credit is spent, the bot first bids and then sends a MessagingEvent
     * through the auction it follows to ask for more money.
     *
     * (you can decide what the bot says in the message)
     */
    def receiveEvent(e: AuctionEvent) = {
      // See the `auction.log.ConsolePrinter` and `backend.sms.SMSService`
      // for example of matching `BidRaised` events.
    }
  }
}