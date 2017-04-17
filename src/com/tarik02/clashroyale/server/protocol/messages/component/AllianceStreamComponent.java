package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceStreamComponent extends Component {
	public long entryId;
	public long senderAvatarId;
	public long senderAvatarId2;
	public String senderName;
	public byte isRemoved;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsLong(entryId);
		stream.putRrsLong(senderAvatarId);
		stream.putRrsLong(senderAvatarId2);
		stream.putString(senderName);
		stream.putByte(isRemoved);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		entryId = stream.getRrsLong();
		senderAvatarId = stream.getRrsLong();
		senderAvatarId2 = stream.getRrsLong();
		senderName = stream.getString();
		isRemoved = stream.getByte();
	}
}
