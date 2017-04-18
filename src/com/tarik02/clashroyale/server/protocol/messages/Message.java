package com.tarik02.clashroyale.server.protocol.messages;

import com.tarik02.clashroyale.server.protocol.Handler;
import com.tarik02.clashroyale.server.utils.DataStream;

public abstract class Message extends Component {
	public final short id;

	protected Message(short id) {
		this.id = id;
	}

	@Override
	public void encode(DataStream stream) {}

	@Override
	public void decode(DataStream stream) {}

	public boolean handle(Handler handler) throws Throwable {
		return false;
	}
}
