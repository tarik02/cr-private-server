package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class TournamentAskJoinable extends ClientMessage {
	public static final short ID = Messages.TOURNAMENT_ASK_JOINABLE;

	public byte unknown_0;
	public byte unknown_1;
	public byte unknown_2;

	public TournamentAskJoinable() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new TournamentAskJoinable();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleTournamentAskJoinable(this);
	}

	@Override
	public void decode(DataStream stream) {
		unknown_0 = stream.getByte();
		unknown_1 = stream.getByte();
		unknown_2 = stream.getByte();
	}
}
