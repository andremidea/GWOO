package br.com.gwoo.auctionSniper;

import br.com.gwoo.auction.Auction;

public class AuctionSniper implements AuctionEventListener{

	private final SniperListener sniperListener;
	private final Auction auction;
	
	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.sniperListener = sniperListener;
		this.auction = auction;
	}

	@Override
	public void auctionClose() {
		sniperListener.sniperLost();
		
	}

	@Override
	public void currentPrice(int price, int increment) {
		auction.bid(price + increment);
		sniperListener.sniperBidding();
	}

}
