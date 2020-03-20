package packet;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread-safe packet of serialized data, categorized by a type, which is some
 * form of enum.
 * 
 * @author Ryder James
 * @since JDK1.8
 * @version 1.1
 *
 * @param <T> - an enum containing type headers for the packet
 */
public class Packet<T extends Enum<T>> implements Serializable, Comparable<Packet<T>> {
	/**
	 * @since version 1.1
	 */
	private static final long serialVersionUID = 7079778524805940740L;

	/**
	 * Keeps a running count of all packets created in this JVM, for purpose of a
	 * unique ID
	 */
	private volatile static AtomicInteger packetCount = new AtomicInteger(0);

	public final Serializable payload;
	public final T type;
	public final int ID;

	/**
	 * Constructs a new <code>Packet</code> with a JVM unique ID.
	 * 
	 * @param header  - the enum constant header of the packet
	 * @param payload
	 */
	public Packet(T header, Serializable payload) {
		this.type = header;
		this.payload = payload;
		this.ID = packetCount.getAndIncrement();
	}

	/**
	 * Returns a <code>String</code> representation of this <code>Packet</code>.
	 */
	@Override
	public String toString() {
		return type.toString() + ":\n" + payload.toString() + "\n";
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object anotherPacket) {
		try {
			return (anotherPacket instanceof Packet<?> && anotherPacket != null
					? compareTo((Packet<T>) anotherPacket) == 0
					: false);
		} catch (ClassCastException e) {
			return false;
		}
	}

	@Override
	public int compareTo(Packet<T> anotherPacket) {
		return anotherPacket != null ? ID - anotherPacket.ID : -1;
	}
}
