package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.AllianceRankingEntry;

public class AllianceLocalRankingList extends Message {
	public static final short ID = Info.ALLIANCE_LOCAL_RANKING_LIST;

	public AllianceRankingEntry[] entries;

	public AllianceLocalRankingList() {
		super(ID);

		entries = new AllianceRankingEntry[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		entries = new AllianceRankingEntry[stream.getBInt()];
		for (int i = 0; i < entries.length; ++i) {
			entries[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		stream.putBInt(entries.length);
		for (int i = 0; i < entries.length; ++i) {
			entries[i] = new AllianceRankingEntry();
			entries[i].decode(stream);
		}
	}
}
