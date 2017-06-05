package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class ChestSeasonRewardOpen extends ClientCommand {
	public static final short ID = Commands.CHEST_SEASON_REWARD_OPEN;

	public ChestSeasonRewardOpen() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new ChestSeasonRewardOpen();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleChestSeasonRewardOpen(this);
	}

	@Override
	public void decode(DataStream stream) {
	}
}
