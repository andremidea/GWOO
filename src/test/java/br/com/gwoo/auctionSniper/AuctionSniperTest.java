package br.com.gwoo.auctionSniper;

import javax.jws.Oneway;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.gwoo.auction.Auction;

@RunWith(JMock.class)
public class AuctionSniperTest {
	private final Mockery context = new Mockery();
	private final SniperListener sniperListener = context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
	
	@Test public void
	reportsLoseWhenAuctionCloses(){
		context.checking(new Expectations(){{
			one(sniperListener).sniperLost();
		}});
		sniper.auctionClose();
	}
	
	@Test public void
	bidsHigherAndReportsBiddingWhenNewPriceArrives(){
		final int price = 1001;
		final int increment = 25;
		context.checking(new Expectations() {{
			one(auction).bid(price + increment);
			atLeast(1).of(sniperListener).sniperBidding();
		}});
		sniper.currentPrice(price, increment);
		}
	}
