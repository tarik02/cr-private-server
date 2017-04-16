package com.tarik02.clashroyale.server;

import java.net.Socket;

public class Player {
	protected Server server;
	protected Socket socket;

	public Player(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	public void close() {

	}
}
