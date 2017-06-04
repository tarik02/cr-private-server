package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

import royaleserver.utils.SCID;

public final class ClanSearch extends ClientMessage {
	public static final short ID = Messages.CLAN_SEARCH;

	public String searchString;
	public SCID desiredRegion;
	public int minMembers;
	public int maxMembers;
	public int minTrophies;
	public boolean findOnlyJoinableClans;
	public int unknown_6;
	public int unknown_7;

	public ClanSearch() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		searchString = stream.getString();
		desiredRegion = stream.getSCID();
		minMembers = stream.getBInt();
		maxMembers = stream.getBInt();
		minTrophies = stream.getBInt();
		findOnlyJoinableClans = stream.getBoolean();
		unknown_6 = stream.getBInt();
		unknown_7 = stream.getBInt();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClanSearch(this);
	}
}
