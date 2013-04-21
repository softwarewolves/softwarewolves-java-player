package softwarewolves.javabot.xmpp;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public interface BotXmppSupportEvents {
	
	/**
	 * *************************************************************************
	 * 
	 * The bot successfully joined the village. 
	 * 
	 * @param mc
	 * @param room 
	 * *************************************************************************
	 */
	
	public void joinedVillage(String mc, String room);
	
	/**
	 * *************************************************************************
	 * 
	 * This player received a private message
	 * 
	 * @param m
	 **************************************************************************
	 */
	public void privateMessageReceived(Message m);
	
	/**
	 * *************************************************************************
	 * 
	 * This player received a message
	 * 
	 * @param chat
	 * @param m
	 * @throws XMPPException
	 **************************************************************************
	 */
	public void chatMessageReceived(Chat chat, Message m) throws XMPPException;
	
	/**
	 * *************************************************************************
	 * This player received a subject change
	 * 
	 * @param subject
	 * @param from
	 **************************************************************************
	 */
	public void subjectChangeReceivedFromVillage(String subject, String from);

	/**
	 * *************************************************************************
	 * This player received a message
	 * 
	 * @param m
	 **************************************************************************
	 */
	public void messageReceivedFromVillage(Message m);

}
