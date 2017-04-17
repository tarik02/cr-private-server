package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class ClientHome extends Component {
	public byte id;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(id);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getByte();
	}
}
