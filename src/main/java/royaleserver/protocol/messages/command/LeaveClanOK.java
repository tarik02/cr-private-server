package royaleserver.protocol.messages.command;

import royaleserver.database.entity.ClanEntity;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class LeaveClanOK extends Command {
	public static final short ID = 205;

	public long allianceId;

	public LeaveClanOK() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		System.out.println(allianceId);

		stream.putLLong(allianceId);

		stream.putRrsInt32(0);
		stream.putRrsInt32(4);

		stream.putByte((byte) 127);
		stream.putByte((byte) 127);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
	}

	public static LeaveClanOK from(ClanEntity entity) {
		LeaveClanOK leaveClanOK = new LeaveClanOK();
		leaveClanOK.allianceId = entity.getId();

		return leaveClanOK;
	}
}
