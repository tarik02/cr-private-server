package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class AskForAllianceData extends Message {
	public static final short ID = Info.ASK_FOR_ALLIANCE_DATA;

	public long allianceId;

	public AskForAllianceData() {
		super(ID);

		allianceId = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(allianceId);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		allianceId = stream.getBLong();
	}

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleAskForAllianceData(this);
	}
}
