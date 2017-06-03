package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class AvatarInGameStatusUpdated extends Message {
	public static final short ID = Info.AVATAR_IN_GAME_STATUS_UPDATED;

	public long avatarId;
	public byte status;

	public AvatarInGameStatusUpdated() {
		super(ID);

		avatarId = 0;
		status = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(avatarId);
		stream.putByte(status);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		avatarId = stream.getBLong();
		status = stream.getByte();
	}
}
