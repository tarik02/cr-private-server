package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class RoyaleTVEntry extends Component {
	public String json;
	public byte serverId;
	public long battleId;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(json);
		stream.putByte(serverId);
		stream.putBLong(battleId);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		json = stream.getString();
		serverId = stream.getByte();
		battleId = stream.getBLong();
	}
}
