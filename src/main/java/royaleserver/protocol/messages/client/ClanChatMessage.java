package royaleserver.protocol.messages.client;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class ClanChatMessage extends Message {
	public static final short ID = Info.CLAN_CHAT_MESSAGE;

	public String message;

	public ClanChatMessage() {
		super(ID);

		message = "";
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		message = stream.getString();
	}
}
