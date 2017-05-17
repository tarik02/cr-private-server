package royaleserver.protocol.messages.component;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class RoyaleTVEntry extends Component {
	public String json;
	public int unknown_1;
	public byte unknown_2;
	public int views;
	public int unknown_4;
	public byte unknown_5;
	public int ageSeconds;
	public int index;
	public byte unknown_8;
	public byte serverId;
	public long battleId;

	public RoyaleTVEntry() {
		json = "";
		unknown_1 = 0;
		unknown_2 = 0;
		views = 0;
		unknown_4 = 0;
		unknown_5 = 0;
		ageSeconds = 0;
		index = 0;
		unknown_8 = 0;
		serverId = 0;
		battleId = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(json);
		stream.putBInt(unknown_1);
		stream.putByte(unknown_2);
		stream.putRrsInt32(views);
		stream.putRrsInt32(unknown_4);
		stream.putByte(unknown_5);
		stream.putRrsInt32(ageSeconds);
		stream.putRrsInt32(index);
		stream.putByte(unknown_8);
		stream.putByte(serverId);
		stream.putBLong(battleId);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		json = stream.getString();
		unknown_1 = stream.getBInt();
		unknown_2 = stream.getByte();
		views = stream.getRrsInt32();
		unknown_4 = stream.getRrsInt32();
		unknown_5 = stream.getByte();
		ageSeconds = stream.getRrsInt32();
		index = stream.getRrsInt32();
		unknown_8 = stream.getByte();
		serverId = stream.getByte();
		battleId = stream.getBLong();
	}
}
