package royaleserver.protocol.messages.command.stream;

import royaleserver.protocol.messages.StreamCommand;
import royaleserver.utils.DataStream;

public class AllianceEventStreamEntry extends StreamCommand {
	public static final short ID = 4;

	public int type;
	public long initiatorId;
	public String initiatorName;

	public AllianceEventStreamEntry() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		// 1 = kicked, 2 =  left, 5 = promoted
		stream.putRrsInt32(type);
		stream.putRrsLong(initiatorId);
		stream.putString(initiatorName);
	}
}
