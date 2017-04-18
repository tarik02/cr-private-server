package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceOnlineStatusUpdated extends Message {
	public static final short ID = Info.ALLIANCE_ONLINE_STATUS_UPDATED;

	public byte membersOnline;
	public byte unknown_1;

	public AllianceOnlineStatusUpdated() {
		super(ID);

		membersOnline = 0;
		unknown_1 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(membersOnline);
		stream.putByte(unknown_1);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		membersOnline = stream.getByte();
		unknown_1 = stream.getByte();
	}
}
