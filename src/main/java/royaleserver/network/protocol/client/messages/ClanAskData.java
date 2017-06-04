package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class ClanAskData extends ClientMessage {
	public static final short ID = Messages.CLAN_ASK_DATA;

	public long clanId;

	public ClanAskData() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new ClanAskData();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClanAskData(this);
	}

	@Override
	public void decode(DataStream stream) {
		clanId = stream.getBLong();
	}
}
