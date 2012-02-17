package br.com.gwoo.integration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.gwoo.infra.ApplicationRunner;
import br.com.gwoo.infra.FakeAuctionServer;
import br.com.gwoo.main.Main;
import br.com.gwoo.main.MainWindow;



public class AuctionSniperEndToEndTest {

	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private final ApplicationRunner application = new ApplicationRunner();
	
	@Test public void sniperJoinsAuctionUntilAuctionsCloses() throws Exception{
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showsSniperHasLostAuction();
		
	}
	
	@Test public void sniperMakesAHigherBidButLoses() throws Exception{
		auction.startSellingItem();
		
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1000,98,"other bidder");
		application.hasShownSniperIsBidding();
		
		auction.hasReceivedBid(1098,ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}
	
	@After public void stopAuction() throws InterruptedException{
		auction.stop();
		Main.ui.dispose();
	}
	
	@Before public void stopApplication(){
		application.stop();		
	}
	
}
