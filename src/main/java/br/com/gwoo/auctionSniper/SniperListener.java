package br.com.gwoo.auctionSniper;

import java.util.EventListener;

public interface SniperListener extends EventListener{
	void sniperLost();

	void sniperBidding();
}
