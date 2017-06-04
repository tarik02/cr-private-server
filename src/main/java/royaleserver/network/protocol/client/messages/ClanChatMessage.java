package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class ClanChatMessage extends ClientMessage {
	public static final short ID = Messages.CLAN_CHAT_MESSAGE;

	public String message;

	public ClanChatMessage() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new ClanChatMessage();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClanChatMessage(this);
	}

	@Override
	public void decode(DataStream stream) {
		message = stream.getString();
	}
}
