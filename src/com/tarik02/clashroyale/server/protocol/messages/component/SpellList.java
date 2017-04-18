package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class SpellList extends Component {
	public SCID card;
	public int unknown_1;

	public SpellList() {
		card = new SCID();
		unknown_1 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putSCID(card);
		stream.putRrsInt32(unknown_1);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		card = stream.getSCID();
		unknown_1 = stream.getRrsInt32();
	}
}
