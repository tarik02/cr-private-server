package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AvatarInGameStatusUpdated extends Message {
	public static final short ID = Info.AVATAR_IN_GAME_STATUS_UPDATED;

	public long avatarId;
	public byte status;

	public AvatarInGameStatusUpdated() {
		super(ID);
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
