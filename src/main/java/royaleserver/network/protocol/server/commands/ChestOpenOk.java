package royaleserver.network.protocol.server.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.server.ServerCommand;
import royaleserver.network.protocol.server.components.ChestItem;
import royaleserver.utils.DataStream;

public final class ChestOpenOk extends ServerCommand {
	public static final short ID = Commands.CHEST_OPEN_OK;

	public int gold;
	public int gems;
	public ChestItem[] items;
	public int chestId;

	public byte unknown_7;
	public byte unknown_8;

	public boolean isDraft;

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
		stream.putBoolean(isDraft);

		stream.putRrsInt32(items.length);
		for (ChestItem chestItem : items) {
			chestItem.encode(stream);
		}

		stream.putByte((byte)127);

		stream.putRrsInt32(gold);
		stream.putRrsInt32(gems);

		stream.putRrsInt32(chestId);

		if (isDraft) {
			stream.putRrsInt32(0);
			stream.putRrsInt32(0);
			stream.putRrsInt32(0);
			stream.putRrsInt32(0);
			stream.putRrsInt32(5787);
			stream.putRrsInt32(10);

			stream.putRrsInt32(-64);
			stream.putRrsInt32(-64);
		}

		stream.putByte(unknown_7);
		stream.putByte(unknown_8);
	}
}
