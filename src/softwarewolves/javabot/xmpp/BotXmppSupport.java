package softwarewolves.javabot.xmpp;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;

/**
 * *************************************************************************
 * 
 * 
 * 
 * @author nelis
 * *************************************************************************
 */
public class BotXmppSupport {

	private XMPPConnection connection;
	private final String gameCoordinator;
	private final String botPwd;
	private MultiUserChat gameChatRoom;
	private final String botUser;
	/**
	 * The listener to the events this support object produces when certain XMPP
	 * messages are received.
	 * 
	 */
	private BotXmppSupportEvents eventListener;
	private String xmppServer;
	
	/**
	 * *************************************************************************
	 * Constructs a new xmpp support object and initializes the connection
	 * 
	 * @param botUsername The username of the bot to play with
	 * @param botPassword The password of the bot to play with
	 * @param gamecoordinator The gamecoordinator of the softwarewolves
	 * @param xmppserver The xmpp server on which the game is played
	 * @throws XMPPException
	 **************************************************************************
	 */
	public BotXmppSupport(String botUsername, String botPassword, String gamecoordinator, String xmppserver) throws XMPPException {
		this.botUser = botUsername;
		botPwd = botPassword;
		this.gameCoordinator = gamecoordinator;
		this.setXmppserver(xmppserver);
		
		initialize();
	}
	
	/**
	 * *************************************************************************
	 * 
	 * @return The username of the bot
	 **************************************************************************
	 */
	public String getUsername() {
		return botUser;
	}
	
	/**
	 * *************************************************************************
	 * Initialize this bot by setting up a connection with the server and 
	 * logging in.
	 * 
	 * @throws XMPPException
	 **************************************************************************
	 */
	
	protected void initialize() throws XMPPException{
		connection = new XMPPConnection(getXmppserver());
		connection.connect();		
		connection.login(getUsername(), getPassword());
	}
	
	/**
	 * *************************************************************************
	 * Return a JID for a given name
	 * 
	 * @param name The name of the user
	 * @return the jid in the format name@xmppserver
	 **************************************************************************
	 */
	private String getJID(String name) {
		return name + "@" + getXmppserver();
		
	}
	
	/**
	 * *************************************************************************
	 * @return the password of the bot user
	 **************************************************************************
	 */
	private String getPassword() {
		return botPwd;
		
	}
	
	/**
	 * *************************************************************************
	 * Listen to invites and join the room when invited. 
	 * 
	 * This is based on MUC (Multi User Chat).
	 * 
	 **************************************************************************
	 */
	public void listenToInvites() {
			MultiUserChat.addInvitationListener(connection, new InvitationListener() {
				
				@Override
				public void invitationReceived(Connection connection, String roomName, String inviter,
						String reason, String password, Message arg5) {
						
						gameChatRoom = new MultiUserChat(connection,roomName);
						try {
							// Make sure not to receive the full room history of messages
							DiscussionHistory history = new DiscussionHistory();
						     history.setMaxStanzas(0);
							
						    // join
							gameChatRoom.join(botUser,"",history,SmackConfiguration.getPacketReplyTimeout());
							// warn the bot you have jointed
							eventListener.joinedVillage(inviter,gameChatRoom.getRoom());
							
							// stop lissening for invites
							MultiUserChat.removeInvitationListener(connection, this);
							
							// add a message listener to receive the messages from the room
							gameChatRoom.addMessageListener(new PacketListener() {
								
								@Override
								public void processPacket(Packet arg0) {
									Message m = ((Message) arg0);
									// exclude the message send by the bot itself
									if(!m.getFrom().endsWith(getUsername())){
										// warn the bot a message has been received
										eventListener.messageReceivedFromVillage(m);
									}
								}
							});
							
							// add a subject change listener to know when the subject changes
							gameChatRoom.addSubjectUpdatedListener(new SubjectUpdatedListener() {
								@Override
								public void subjectUpdated(String arg0, String arg1) {
									eventListener.subjectChangeReceivedFromVillage(arg0,arg1);
									
								}
							});
							
						} catch (XMPPException e) {
							e.printStackTrace();
						}
					
				}
			}); 
			
		}
	
	/**
	 * *************************************************************************
	 * Ask to start a new game to the game coordinator
	 * 
	 * @param events The object that will capture the game events
	 * @throws XMPPException
	 **************************************************************************
	 */
	public void askForNewGame(final BotXmppSupportEvents events) throws XMPPException{
		this.eventListener = events;
		ChatManager chatmanager = connection.getChatManager();
		Chat newChat = chatmanager.createChat(getJID(gameCoordinator), new MessageListener() {
		    public void processMessage(Chat chat, Message message) {
		        try {
					events.chatMessageReceived(chat, message);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
		
		chatmanager.addChatListener(new ChatManagerListener() {
			
			@Override
			public void chatCreated(Chat chat, boolean arg1) {
				chat.addMessageListener(new MessageListener() {
					
					@Override
					public void processMessage(Chat chat, Message m) {
						try {
							events.chatMessageReceived(chat,m);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
					}
				});
				
			}
		});
		newChat.sendMessage("I want to play");	
	}

	
	/**
	 * *************************************************************************
	 * Send a message to the whole village
	 * 
	 * @param message The message to send
	 **************************************************************************
	 */
	
	public void sendMessageToVillage(String message) {
		try {
			gameChatRoom.sendMessage(message);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/***************************************************************************
	 * Returns the XMPP server for this game
	 * 
	 * @return the xmppserver
	 ***************************************************************************/
	public String getXmppserver() {
		return xmppServer;
	}
	
	
	/***************************************************************************
	 * Sets the XMPP server for this game
	 * @param xmppserver the xmppserver to set
	 ***************************************************************************/
	protected void setXmppserver(String xmppserver) {
		this.xmppServer = xmppserver;
	}

}
