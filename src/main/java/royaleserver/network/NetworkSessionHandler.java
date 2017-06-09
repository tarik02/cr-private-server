package royaleserver.network;

import royaleserver.network.protocol.Message;

public interface NetworkSessionHandler {
	void sendMessage(Message message);
	void close();
}
