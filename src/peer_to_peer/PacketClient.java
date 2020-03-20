package peer_to_peer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import interfaces.NetSubscriber;
import packet.Packet;
import packet.PacketHandler;

public abstract class PacketClient<T extends Enum<T>> implements Runnable {
	protected Socket clientSocket;
	protected ObjectInputStream input;
	protected ObjectOutputStream output;

	protected volatile boolean isOpen;

	private final PacketHandler<T> handler;

	private NetSubscriber subscriber;
	private Packet<T> prevPacket;

	protected PacketClient(PacketHandler<T> handler) {
		this.handler = handler;
		isOpen = false;
	}

	public abstract void open() throws IOException;

	@Override
	public synchronized void run() {
		while (isOpen()) {
			Packet<T> p = receivePacket();
			if (p != null && !p.equals(prevPacket)) {
				handler.handlePacket(p);
				prevPacket = p;
			}
		}
	}

	public void setNetSubscriber(NetSubscriber sub) {
		subscriber = sub;
	}

	public void sendPacket(Packet<T> p) {
		try {
			output.writeObject(p);
		} catch (IOException e) {
			System.err.println("Sending failed of packet:\n" + p);
			e.printStackTrace();
			close();
		}
	}

	@SuppressWarnings("unchecked")
	public Packet<T> receivePacket() {
		Packet<T> packet = null;

		try {
			packet = (Packet<T>) input.readObject();
		} catch (EOFException | ClassNotFoundException | ClassCastException e) {
			packet = null;
		} catch (SocketException e) {
			packet = null;
			close();
		} catch (IOException e) {
			packet = null;
			e.printStackTrace();
			close();
		}

		return packet;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void close() {
		try {
			if (clientSocket != null)
				clientSocket.close();
			if (input != null)
				input.close();
			if (output != null)
				output.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		isOpen = false;

		subscriber.enableConnections(true);
	}
}
