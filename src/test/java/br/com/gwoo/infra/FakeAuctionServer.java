package br.com.gwoo.infra;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;

import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import br.com.gwoo.main.Main;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FakeAuctionServer {

	public static final String XMPP_HOSTNAME = "localhost";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	private static final String AUCTION_PASSWORD = "auction";
	
	private final String itemId;
	private final XMPPConnection connection;
	private Chat currentChat;
	private final  SingleMessageListener messageListener = new SingleMessageListener();

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		this.connection = new XMPPConnection(new ConnectionConfiguration("localhost", 5222));
	}
	
	public void startSellingItem() throws XMPPException{
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, getItemId()),AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(
				new ChatManagerListener() {
					@Override
					public void chatCreated(Chat chat, boolean createdLocally) {
						currentChat = chat;
						chat.addMessageListener(messageListener);
					}
			});		
	}

	public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		currentChat.sendMessage(String.format("SOLVersion: 1.1; Event: PRICE; " +
				"CurrentPrice: %d; Increment: %d; Bidder: %s;",price,increment,bidder));
	}
	
	public void hasReceivedJoinRequestFromSniper(String sniperId) throws InterruptedException{
		receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
	}
	
	public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(String.format(Main.BID_COMMAND_FORMAT, bid)));
	}
	
	private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) throws InterruptedException {
		messageListener.receivesAMessage(messageMatcher);
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
	}

	public void announceClosed() throws XMPPException{
		currentChat.sendMessage(Main.CLOSE_COMMAND_FORMAT);
	}
	
	public void stop(){
		connection.disconnect();	
	}
	

	public String getItemId() {
		return itemId;
	}

	


}
