

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	// The Server that spawned us
	private Server server;
	// The Socket connected to our client
	private Socket socket;

	// Constructor.
	public ServerThread(Server server, Socket socket) {
		// Save the parameters
		this.server = server;
		this.socket = socket;
		// Start up the thread
		start();
	}

	// This runs in a separate thread when start() is called in the
	// constructor.
	public void run() {
		try {
			// Create a DataInputStream for communication; the client
			// is using a DataOutputStream to write to us
			ObjectInputStream din = new ObjectInputStream(socket.getInputStream());
			// Over and over, forever ...
			while (true) {
				// ... read the next message ...
				//String message = din.toString();
				Player player = (Player) din.readObject();
				// ... tell the world ...
				System.out.println("Object received = " + player.toString());
				// ... and have the server send it to all clients
				server.sendToAll(player);
			}
		} catch (EOFException ie) {
			// This doesn't need an error message
		} catch (IOException ie) {
			// This does; tell the world!
			ie.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// The connection is closed for one reason or another,
			// so have the server dealing with it
			server.removeConnection(socket);
		}
	}
}