package tests

import scala.collection.mutable.Map
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalamock.scalatest.MockFactory
import auction._
import auction.event._
import auction.bots.BidBot
import auction.event.MessagingEvent

/**
 * Tests for BidBot
 */
@RunWith(classOf[JUnitRunner])
class BotTest extends FlatSpec with Matchers with MockFactory {

  // Tämä luokka on avuksi harjoituksessa ja sitä ei tarvitse muuttaa:
  /**
   * A mock version of Auction to record outgoing bids.
   */
  class MockAuction extends Auction with Dispatcher {
    val makeBidRecorder = mockFunction[User, AuctionItem, Int, Boolean]('makeBid)

    override def makeBid(who: User, item: AuctionItem, amount: Int) = {
      // We use mockedMakeBid to record calls, but eventually call
      // the actual method. This kind of a method double is a _spy_.
      makeBidRecorder(who, item, amount)
      super.makeBid(who, item, amount)
    }
  }

  "BidBot" should "make a bid if the owner has money and someone else makes a new best bid" in {
    val auction = new MockAuction()
    // Vinkki: Käytä testeissä auction.makeBidRecorder funktiota asettamalla sille
    // AuctionTest luokan tapaan odotuksia (expects) ennen kuin aloitat huutamisen.
    ???
  }

  "BidBot" should "not bid if the owner has the best bid" in {
    val auction = new MockAuction()
    ???
  }

  "BidBot" should "not bid over the limit" in {
    val auction = new MockAuction()
    ???
  }

  "BidBot" should "not bid if the owner has no money" in {
    val auction = new MockAuction()
    ???
  }

  "BidBot" should "ask for more money after using the last credit" in {
    val auction = new Auction with Dispatcher
    // Vinkki: Voit joko kirjoittaa oman kuuntelijan dispatcherille tai korvata
    // dispatcherin kuten luokassa AuctionTest.
    ???
  }

}