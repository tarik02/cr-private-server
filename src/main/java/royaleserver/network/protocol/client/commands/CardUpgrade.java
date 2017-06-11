package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public final class CardUpgrade extends ClientCommand {
	public static final short ID = Commands.CARD_UPGRADE;

	public int tickStart;
	public int tickEnd;

	public long accountID;
	public SCID cardSCID;

	public CardUpgrade() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new CardUpgrade();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleCardUpgrade(this);
	}

	@Override
	public void decode(DataStream stream) {
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountID = stream.getRrsLong();
		cardSCID = stream.getSCID();
	}
}
