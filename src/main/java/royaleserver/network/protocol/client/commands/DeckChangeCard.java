package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class DeckChangeCard extends ClientCommand {
	public static final short ID = Commands.DECK_CHANGE_CARD;

	public int tickStart;
	public int tickEnd;

	public long accountId;
	public int cardIndex;
	public int slot;
	public int slot2;

	public DeckChangeCard() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new DeckChangeCard();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleDeckChangeCard(this);
	}

	@Override
	public void decode(DataStream stream) {
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountId = stream.getRrsLong();
		cardIndex = stream.getRrsInt32();
		slot = stream.getRrsInt32();
		slot2 = stream.getRrsInt32();
	}
}
