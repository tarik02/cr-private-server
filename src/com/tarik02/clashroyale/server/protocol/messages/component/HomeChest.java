package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;
import com.tarik02.clashroyale.server.utils.Hex;

public class HomeChest extends Component {
	public static final byte STATUS_STATIC = 0x00;
	public static final byte STATUS_OPENED = 0x01;
	public static final byte STATUS_OPENING = 0x08;

	public byte status;
	public int ticksToOpen; // Remains ticks to open
	public int openTicks; // Ticks to open from zero

	public HomeChest() {
		status = STATUS_STATIC;
		ticksToOpen = 0;
		openTicks = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(status);
		if (status == STATUS_OPENING) {
			stream.putRrsInt32(ticksToOpen);
			stream.putRrsInt32(openTicks);
		}
		stream.put(Hex.toByteArray("87 B1 91 0B"));
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}

