package royaleserver.network.protocol.server.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.server.ServerCommand;
import royaleserver.network.protocol.server.components.ChestItem;
import royaleserver.utils.DataStream;

public final class ChestOpenOk extends ServerCommand {
	public static final short ID = Commands.CHEST_OPEN_OK;

	public int gold;
	public int gems;
	public ChestItem[] chestItems;

	public ChestOpenOk() {
		super(ID);
	}

	@Override
	public ServerCommand create() {
		return new ChestOpenOk();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putByte((byte)1);
		stream.putByte((byte)0);

		stream.putRrsInt32(chestItems.length);
		for (ChestItem chestItem : chestItems) {
			chestItem.encode(stream);
		}

		stream.putByte((byte)127);

		stream.putRrsInt32(gold);
		stream.putRrsInt32(gems);

		stream.putRrsInt32(500);

		stream.putByte((byte)2);
		stream.putByte((byte)2);
	}
}
