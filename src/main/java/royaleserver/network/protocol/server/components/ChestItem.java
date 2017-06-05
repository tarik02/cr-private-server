package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;

public class ChestItem {
	public int card;
	public byte unknown_1;
	public int unknown_2;
	public int count;
	public byte unknown_4;
	public byte unknown_5;
	public byte cardOrder;

	public void encode(DataStream stream) {
		stream.putRrsInt32(card);
		stream.putByte(unknown_1);
		stream.putRrsInt32(unknown_2);
		stream.putRrsInt32(count);
		stream.putByte(unknown_4);
		stream.putByte(unknown_5);
		stream.putByte(cardOrder);
	}
}
