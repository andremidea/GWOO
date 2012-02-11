package br.com.gwoo.integration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.gwoo.infra.ApplicationRunner;
import br.com.gwoo.infra.FakeAuctionServer;



public class AuctionSniperEndToEndTest {

	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private final ApplicationRunner application = new ApplicationRunner();
	
	@Test public void sniperJoinsAuctionUntilAuctionsCloses() throws Exception{
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestForSniper();
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}
	
	@After public void stopAuction(){
		auction.stop();
	}
	
	@Before public void stopApplication(){
		application.stop();
	}
	
}
