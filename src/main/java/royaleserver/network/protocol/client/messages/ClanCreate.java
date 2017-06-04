package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public final class ClanCreate extends ClientMessage {
	public static final short ID = Messages.CLAN_CREATE;

	public static final int TYPE_OPEN = 1;
	public static final int TYPE_INVITE = 2;
	public static final int TYPE_CLOSED = 3;

	public String name;
	public String description;
	public SCID badge;
	public int type;
	public int minTrophies;
	public SCID location;

	public ClanCreate() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		name = stream.getString();
		description = stream.getString();
		badge = stream.getSCID();
		type = stream.getRrsInt32();
		minTrophies = stream.getRrsInt32();
		location = stream.getSCID();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClanCreate(this);
	}
}
