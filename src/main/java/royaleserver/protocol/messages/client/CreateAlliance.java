package royaleserver.protocol.messages.client;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.MessageHandler;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class CreateAlliance extends Message {
	public static final short ID = Info.CREATE_ALLIANCE;

	public String name;
	public String description;
	public SCID badge;
	public int type; // open = 1, invite = 2, closed = 3
	public int minTrophies;
	public SCID location;

	public CreateAlliance() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		name = stream.getString();
		description = stream.getString();
		badge = stream.getSCID();
		type = stream.getRrsInt32();
		minTrophies = stream.getRrsInt32();
		location = stream.getSCID();
	}

	@Override
	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleCreateAlliance(this);
	}
}
