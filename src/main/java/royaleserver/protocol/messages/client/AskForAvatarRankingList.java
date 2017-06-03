package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class AskForAvatarRankingList extends Message {
	public static final short ID = Info.ASK_FOR_AVATAR_RANKING_LIST;

	public byte unknown_0;

	public AskForAvatarRankingList() {
		super(ID);

		unknown_0 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(unknown_0);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		unknown_0 = stream.getByte();
	}

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleAskForAvatarRankingList(this);
	}
}
