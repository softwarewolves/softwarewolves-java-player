package softwarewolves.javabot;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import softwarewolves.javabot.xmpp.BotXmppSupport;
import softwarewolves.javabot.xmpp.BotXmppSupportEvents;


/**
 * *************************************************************************
 * A simple implementation of a bot in the softwarewolves game. 
 * 
 * This bot implements the "lazy villager" strategy, i.e. the bot starts the 
 * game and enters the chatroom.
 * 
 * This class uses the BotXmppSupport and BotXmppSupportEvents classes, who 
 * provide an abstraction on the Xmpp details.
 * 
 * @author nelis
 * 
 **************************************************************************
 */
public class Bot implements BotXmppSupportEvents {

	public static final String XMPP_SERVER = "awtest1.vm.bytemark.co.uk";
	public static final String BOTUSERNAME = "javabot";
	public static final String BOTPWD = "javabot";
	public static final String GAMECOORDINATOR = "sww";
	
	/**
	 * The master of ceremony of the werewolves game.
	 */
	private String mc;
	/**
	 * A basic class providing abstraction of xmpp
	 */
	private final BotXmppSupport support;
	/**
	 * An indication if this bot is the werewolve or not
	 */
	private boolean werewolf;

	
	public Bot(String botusername, String botpasswd, String gamecoordinator, String xmppserver) throws XMPPException{
		this.support = new BotXmppSupport(botusername, botpasswd,gamecoordinator,xmppserver);
	}
	
	public void play() throws XMPPException{
		support.askForNewGame(this);
		support.listenToInvites();
	}

	/**
	 * *************************************************************************
	 * Main to start a bot
	 * 
	 * @param args
	 * @throws XMPPException
	 * @throws InterruptedException
	 **************************************************************************
	 */
	public static void main(String[] args) throws XMPPException, InterruptedException{
		
		/**
		 * This project uses the Smack library for xmpp support. Setting the 
		 * Connection.DEBUG_ENABLED to true triggers a debug screen to pop-up to 
		 * help with debugging by displaying all messages send and received.
		 */
		Connection.DEBUG_ENABLED = true;
		
		
		/**
		 * Construct a bot and start playing
		 */
		Bot javabot = new Bot(BOTUSERNAME, BOTPWD,GAMECOORDINATOR,XMPP_SERVER);
		javabot.play();
		
		/**
		 * Keep the program running, otherwise you cannot receive events from xmpp anymore.
		 */
		while(true){
		}
	}

	@Override
	public void joinedVillage(String mc) {
		System.out.println("Invited in village by "+mc);
		this.mc = mc;
		support.sendMessageToVillage("Howdy!");
	}

	@Override
	public void privateMessageReceived(Message m) {
		System.out.println("Private Message received " + m.toString());		
	}

	@Override
	public void messageReceived(Chat chat, Message m) throws XMPPException {
		System.out.println("From "+ m.getFrom() + " received message "+ m.getBody());
		
		if(m.getFrom().equals(mc)){
			if(m.getBody().startsWith("You are selected as werewolf for this game")){
				werewolf = true;
			}
		
			if (werewolf && m.getBody().startsWith("Please choose who you want to eat")){
				String[] lines = m.getBody().split(":");
				String[] players = lines[1].split(",");
				chat.sendMessage("I eat "+players[0]);	
			}
		}
	}
	
	@Override
	public void subjectChangeReceived(String subject, String from) {
		System.out.println("Subject: " + subject + " From:  " + from);
		
	}

	@Override
	public void messageReceived(Message m) {
		String messageBody = m.getBody();
		System.out.println(m.getFrom() + ": " + messageBody);
		
//		if(m.getFrom().equals(mc)){		
			if (messageBody.startsWith("Please vote who should be hanged:")){
				String[] lines = messageBody.split(":");
				String[] players = lines[1].split(",");
				support.sendMessageToVillage("I vote for "+players[0]);	
			}
//		}
	}
}
