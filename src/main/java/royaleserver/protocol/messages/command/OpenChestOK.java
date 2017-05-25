package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.component.ChestItem;
import royaleserver.utils.DataStream;

public class OpenChestOK extends Command {
	public static final short ID = 210;

	public ChestItem[] chestItems;
	int cardsCount;

	int gold;
	int gems;

	public OpenChestOK() {
		super(ID);

		cardsCount = 40;

		gold = 1000;
		gems = 1500;

		chestItems = new ChestItem[cardsCount];

		for (int i = 0; i < cardsCount; i++) {
			chestItems[i] = new ChestItem();
			chestItems[i].card = i + 1;
			chestItems[i].unknown_1 = 0;
			chestItems[i].unknown_2 = 0;
			chestItems[i].quantity = 10;
			chestItems[i].unknown_4 = 0;
			chestItems[i].unknown_5 = 0;
			chestItems[i].unknown_6 = 0;
		}
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

		stream.putByte((byte)2);
		stream.putByte((byte)2);
	}
}
