package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceMemberEntry extends Component {
	public long avatarId;
	public String facebookId;
	public String name;
	public long arena;
	public byte role;
	public byte currenRank;
	public byte previousRank;
	public long homeID;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(avatarId);
		stream.putString(facebookId);
		stream.putString(name);
		stream.putSCID(arena);
		stream.putByte(role);
		stream.putByte(currenRank);
		stream.putByte(previousRank);
		stream.putBLong(homeID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		avatarId = stream.getBLong();
		facebookId = stream.getString();
		name = stream.getString();
		arena = stream.getSCID();
		role = stream.getByte();
		currenRank = stream.getByte();
		previousRank = stream.getByte();
		homeID = stream.getBLong();
	}
}
