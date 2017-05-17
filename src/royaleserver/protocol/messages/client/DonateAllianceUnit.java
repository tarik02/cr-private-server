package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class DonateAllianceUnit extends Message {
	public static final short ID = Info.DONATE_ALLIANCE_UNIT;

	public byte type;
	public byte spell;
	public byte unknown_2;
	public byte unknown_3;
	public byte unknown_4;
	public byte unknown_5;
	public long streamID;

	public DonateAllianceUnit() {
		super(ID);

		type = 0;
		spell = 0;
		unknown_2 = 0;
		unknown_3 = 0;
		unknown_4 = 0;
		unknown_5 = 0;
		streamID = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(type);
		stream.putByte(spell);
		stream.putByte(unknown_2);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
		stream.putByte(unknown_5);
		stream.putBLong(streamID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		type = stream.getByte();
		spell = stream.getByte();
		unknown_2 = stream.getByte();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
		unknown_5 = stream.getByte();
		streamID = stream.getBLong();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleDonateAllianceUnit(this);
	}
}
