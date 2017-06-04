package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.utils.DataStream;

public final class ClanJoin extends ClientMessage {
	public static final short ID = Messages.CLAN_JOIN;

	public long clanId;
	public byte unknown_1;
	public byte unknown_2;
	public byte unknown_3;
	public byte unknown_4;

	public ClanJoin() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new ClanJoin();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClanJoin(this);
	}

	@Override
	public void decode(DataStream stream) {
		clanId = stream.getBLong();
		unknown_1 = stream.getByte();
		unknown_2 = stream.getByte();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
	}
}
