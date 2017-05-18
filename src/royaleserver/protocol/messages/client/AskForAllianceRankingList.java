package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class AskForAllianceRankingList extends Message {
	public static final short ID = Info.ASK_FOR_ALLIANCE_RANKING_LIST;

	public byte region;

	public AskForAllianceRankingList() {
		super(ID);

		region = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(region);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		region = stream.getByte();
	}

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleAskForAllianceRankingList(this);
	}
}
