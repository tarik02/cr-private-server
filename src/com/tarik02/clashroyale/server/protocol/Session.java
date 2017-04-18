package com.tarik02.clashroyale.server.protocol;

import com.tarik02.clashroyale.server.protocol.messages.Message;

public interface Session {
	void sendMessage(Message message);
}
