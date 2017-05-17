package royaleserver.protocol;

import royaleserver.protocol.messages.Message;

public interface Session {
	void sendMessage(Message message);
	void close();
}
