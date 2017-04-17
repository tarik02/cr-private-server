package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceOnlineStatusUpdated extends Message {
	public static final short ID = Info.ALLIANCE_ONLINE_STATUS_UPDATED;

	public byte membersOnline;

	public AllianceOnlineStatusUpdated() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(membersOnline);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		membersOnline = stream.getByte();
	}
}
