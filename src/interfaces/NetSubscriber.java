package interfaces;

/**
 * A subscriber for use with a <code>PacketClient</code>. Defines a single
 * method in which you can enable or disable connections on the subscriber.
 * Generally speaking, a <code>NetSubscriber</code> will disallow all
 * connections while there is an existing established connection between 2
 * peer-to-peer <code>PacketClient</code>s.
 * 
 * @author Ryder James
 * @see peer_to_peer.PacketClient
 * @since JDK1.8
 * @version 1
 */
public interface NetSubscriber {
	/**
	 * Whether or not to allow new incoming and outgoing connection attempts.
	 * 
	 * @param enable - if <code>true</code>, allows new incoming and outgoing
	 *               connection attempts
	 */
	void enableConnections(boolean enable);
}
