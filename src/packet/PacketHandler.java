package packet;

/**
 * This interfaces provides a guarantee that an implementor knows how to work
 * with packets of a given type.
 * 
 * @author Ryder James
 * @since JDK1.8
 * @version 1
 *
 * @param <T> - an enum containing type headers for the packet
 */
@FunctionalInterface
public interface PacketHandler<T extends Enum<T>> {
	void handlePacket(Packet<T> packet);
}
