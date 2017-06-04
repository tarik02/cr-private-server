package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class TournamentAskJoinable extends ClientMessage {
	public static final short ID = Messages.TOURNAMENT_ASK_JOINABLE;

	public TournamentAskJoinable() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
	}

	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleTournamentAskJoinable(this);
	}
}
