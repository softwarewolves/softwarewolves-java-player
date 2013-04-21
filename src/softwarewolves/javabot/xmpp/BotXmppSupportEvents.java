package softwarewolves.javabot.xmpp;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public interface BotXmppSupportEvents {
	
	/**
	 * *************************************************************************
	 * The bot successfully joined the village. 
	 * 
	 * @param mc
	 **************************************************************************
	 */
	
	public void joinedVillage(String mc);
	
	/**
	 * *************************************************************************
	 * 
	 * This player received 
	 * 
	 * @param m
	 **************************************************************************
	 */
	public void privateMessageReceived(Message m);
	
	public void messageReceived(Chat chat, Message m) throws XMPPException;
	
	public void subjectChangeReceived(String subject, String from);

	public void messageReceived(Message m);

}
