package royaleserver.network;

import royaleserver.network.protocol.Message;

public interface NetworkSession {
	void sendMessage(Message message);
	void close();
}
