package peer_to_peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import packet.PacketHandler;

public class HostClient<T extends Enum<T>> extends PacketClient<T> {
	private final int port;

	private ServerSocket serverSocket;

	public HostClient(int port, PacketHandler<T> handler) {
		super(handler);
		this.port = port;
	}

	@Override
	public void open() throws IOException {
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();

		output = new ObjectOutputStream(clientSocket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(clientSocket.getInputStream());

		isOpen = true;
	}

	@Override
	public void close() {
		try {
			serverSocket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		super.close();
	}
}
