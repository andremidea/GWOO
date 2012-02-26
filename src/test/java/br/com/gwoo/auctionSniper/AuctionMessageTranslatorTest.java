package br.com.gwoo.auctionSniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.gwoo.main.Main;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
	private final Mockery context = new Mockery();
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	
			
	public static final Chat UNUSED_CHAT = null;
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

	@Test public void
	notifiesAuctionClosedWhenCloseMessageReceived(){
		context.checking(new Expectations(){{
			one(listener).auctionClose();
		}});
				
		Message message = new Message();
		message.setBody(Main.CLOSE_COMMAND_FORMAT);
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test public void
	notifiesBidDetailsWhenCurrentPriceMessageReceived(){
		context.checking(new Expectations(){{
			exactly(1).of(listener).currentPrice(192, 7);
		}});
	
	Message message = new Message();
	message.setBody(String.format(Main.PRICE_COMMAND_FORMAT, 192,7,"Someone else"));
	
	translator.processMessage(UNUSED_CHAT, message);
	
	}
}
