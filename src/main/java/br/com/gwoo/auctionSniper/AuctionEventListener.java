package br.com.gwoo.auctionSniper;

public interface AuctionEventListener {

	public void auctionClose();

	public void currentPrice(int i, int j);

}
