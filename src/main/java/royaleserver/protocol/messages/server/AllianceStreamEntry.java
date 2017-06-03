package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.AllianceStreamComponent;

public class AllianceStreamEntry extends Message {
	public static final short ID = Info.ALLIANCE_STREAM_ENTRY;

	public AllianceStreamComponent entry;

	public AllianceStreamEntry() {
		super(ID);

		entry = new AllianceStreamComponent();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		entry.encode(stream);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		entry.decode(stream);
	}
}
