package tests

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner

import auction._
import auction.event._

/**
 * Tests for Auction
 *
 * Includes three functional sample tests. Second sample demonstrates using
 * mock functions and third sample demonstrates using stub functions.
 *
 * Half of the exercise is to write an implementation of the tests in this
 * class including the fail directive. All these tests will and should fail
 * until the bugs they reveal are fixed in the `Auction` and `AuctionItem`.
 * Fixing the bugs is the second simultaneous half of the exercise.
 */
@RunWith(classOf[JUnitRunner])
class AuctionTest extends FlatSpec with Matchers with MockFactory {

  // Tämä piirreluokka on avuksi harjoituksessa ja sitä ei tarvitse muuttaa:
  /**
   * For making expectations of what should happen in the test case
   * The expectations are set BEFORE the methods are called.
   */
  trait MockedDispatcher extends Dispatcher {
    val mf = mockFunction[AuctionEvent, Unit]('fireEvent)

    override def registerListener(l: Listener) = {}
    override def removeListener(l: Listener) = {}
    override def fireEvent(e: AuctionEvent) = mf(e)
    override def receiveEvent(e: AuctionEvent) = {}
  }

  // Tämä piirreluokka on avuksi harjoituksessa ja sitä ei tarvitse muuttaa:
  /**
   * For verifying later what happened in the test case
   * The verifications are defined AFTER the methods are called.
   */
  trait StubbedDispatcher extends Dispatcher {
    val sf = stubFunction[AuctionEvent, Unit]('fireEvent)

    override def registerListener(l: Listener) = {}
    override def removeListener(l: Listener) = {}
    override def fireEvent(e: AuctionEvent) = sf(e)
    override def receiveEvent(e: AuctionEvent) = {}
  }

  "Auction" should "remove an auction from the list of open auctions when it's closed in Auction" in {
    val testAuction = new Auction with Dispatcher
    val james = new User("James", 123)
    val sofa = testAuction.addAuction(james, "blue sofa", 100, 5)

    // Precondition, if we are going to check if a sofa is removed, better see that it is there.
    assume(testAuction.getOpenAuctions.contains(sofa))

    // Now remove it.
    testAuction.closeAuction(sofa)

    // Now test it is not there.
    assert(!testAuction.getOpenAuctions.contains(sofa))
  }

  "Auction" should "close the auction item when it's closed in Auction" in {
    fail // remove this line and write a proper implementation
  }

  "Auction" should "send a BidRaised event when someone makes an accepted bid" in {
    val testAuction = new Auction with MockedDispatcher
    val james = new User("James", 123)
    val pete = new User("Pete", 456)
    //testAuction.mf expects (where { x: AuctionEvent => x.isInstanceOf[NewAuction] })
    val sofa = testAuction.addAuction(james, "blue sofa", 100, 5)

    // Tell the mock what to expect.
    testAuction.mf expects (BidRaised(new Bid(sofa, pete, 200)))

    // Bid enough that it succeeds.
    testAuction.makeBid(pete, sofa, 200)
  }

  "Auction" should "send the BidRaised event before the BidBeaten event" in {
    // HUOM! Katso scalaMock:in tutoriaalista miten `inSequence` toimii
    // Tarvitset sitä tässä testissä.

    fail // remove this line and write a proper implementation
  }

  "Auction" should "send a NewAuction event to promote newly opened Auctions" in {
    // This test shows the other possible way to use mocks - Verification after
    // execution of tested code. Scalamock uses the word stub to refer to these.
    val testAuction = new Auction with StubbedDispatcher
    val james = new User("James", 123)

    val sofa = testAuction.addAuction(james, "blue sofa", 100, 5)
    testAuction.sf.verify(NewAuction(sofa))

    // HUOM! Kun korjaat tämän testin osoittaman virheen koodista, muut testit
    // voivat mennä "rikki". Tämä on normaalia ohjelmaa kehittäessä. Päivitä muut
    // testit niin että ne osaavat ohittaa NewAuction-eventin ennen kuin alkavat
    // oman testaustyönsä. Alla oleva rivi hyväksyy metodikutsun vain jos sen
    // parametri on NewAuction-tyyppinen. Käytä sitä.
    //
    //   testAuction.mf expects (where {x:AuctionEvent => x.isInstanceOf[NewAuction]})
  }

  "AuctionItem" should "not accept a bid equal to starting bid" in {
    fail // remove this line and write a proper implementation
  }

  "Auction" should "not accept a bid after the item is closed" in {
    fail // remove this line and write a proper implementation
  }

  "AuctionItem" should "not allow the owner to bid" in {
    fail // remove this line and write a proper implementation
  }
}
