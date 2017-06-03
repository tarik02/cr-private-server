package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.AllianceHeaderEntry;

public class JoinableAllianceList extends Message {
	public static final short ID = Info.JOINABLE_ALLIANCE_LIST;

	public AllianceHeaderEntry[] alliances;

	public JoinableAllianceList() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(alliances.length);

		for (int i = 0; i < alliances.length; ++i) {
			alliances[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		stream.putRrsInt32(alliances.length);
		for (int i = 0; i < alliances.length; ++i) {
			alliances[i] = new AllianceHeaderEntry();
			alliances[i].decode(stream);
		}
	}
}
