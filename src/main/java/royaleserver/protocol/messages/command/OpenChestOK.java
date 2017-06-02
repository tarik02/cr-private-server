package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.component.ChestItem;
import royaleserver.utils.DataStream;

public class OpenChestOK extends Command {
	public static final short ID = 210;

	public int gold;
	public int gems;
	public ChestItem[] chestItems;

	public OpenChestOK() {
		super(ID);

		gold = 1000;
		gems = 1500;

		chestItems = new ChestItem[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

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

		stream.putByte((byte)4);
		stream.putByte((byte)2);
	}
}
