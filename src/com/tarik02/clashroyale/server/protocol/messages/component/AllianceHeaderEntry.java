package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class AllianceHeaderEntry extends Component {
	public long Id;
	public String name;
	public SCID badge;
	public byte type;
	public byte numberOfMembers;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(Id);
		stream.putString(name);
		stream.putSCID(badge);
		stream.putByte(type);
		stream.putByte(numberOfMembers);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		Id = stream.getBLong();
		name = stream.getString();
		badge = stream.getSCID();
		type = stream.getByte();
		numberOfMembers = stream.getByte();
	}
}
