package peer_to_peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import packet.PacketHandler;

public class GuestClient<T extends Enum<T>> extends PacketClient<T> {
	private final String hostName;
	private final int port;

	public GuestClient(String hostName, int port, PacketHandler<T> handler) {
		super(handler);
		this.hostName = hostName;
		this.port = port;
	}

	@Override
	public void open() throws IOException {
		clientSocket = new Socket();
		while (!clientSocket.isConnected()) {
			try {
				clientSocket.connect(new InetSocketAddress(hostName, port), 1000);
			} catch (SocketTimeoutException e) {
				clientSocket = new Socket();
			}
		}
		
		output = new ObjectOutputStream(clientSocket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(clientSocket.getInputStream());

		isOpen = true;
	}
}
