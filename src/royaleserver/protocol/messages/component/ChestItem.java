package royaleserver.protocol.messages.component;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class ChestItem extends Component {
	public int card;
	public byte unknown_1;
	public int unknown_2;
	public int quantity;
	public byte unknown_4;
	public byte unknown_5;
	public byte unknown_6;

	public ChestItem() {
		card = 0;
		unknown_1 = 0;
		unknown_2 = 0;
		quantity = 0;
		unknown_4 = 0;
		unknown_5 = 0;
		unknown_6 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(card);
		stream.putByte(unknown_1);
		stream.putRrsInt32(unknown_2);
		stream.putRrsInt32(quantity);
		stream.putByte(unknown_4);
		stream.putByte(unknown_5);
		stream.putByte(unknown_6);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		card = stream.getRrsInt32();
		unknown_1 = stream.getByte();
		unknown_2 = stream.getRrsInt32();
		quantity = stream.getRrsInt32();
		unknown_4 = stream.getByte();
		unknown_5 = stream.getByte();
		unknown_6 = stream.getByte();
	}
}
