package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;

public class Card {
	public static final int LABEL_NONE = 0;
	public static final int LABEL_UPGRADABLE = 1;
	public static final int LABEL_NEW = 2;

	public royaleserver.logic.Card card;
	public int level;
	public int count;
	public int label;
	public int boughtTimes; // in shop
	public int unknown_2;
	public int unknown_3;

	public void encode(DataStream stream) {
		stream.putRrsInt32(card == null ? 1 : card.getIndex()); // If 0 then crash
		stream.putRrsInt32(level - 1);
		stream.putRrsInt32(boughtTimes);
		stream.putRrsInt32(count);
		stream.putRrsInt32(unknown_2);
		stream.putByte((byte)label);
		stream.putRrsInt32(unknown_3);
	}
}
