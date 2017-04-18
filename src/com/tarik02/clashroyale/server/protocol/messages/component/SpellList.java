package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class SpellList extends Component {
	public SCID card;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putSCID(card);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		card = stream.getSCID();
	}
}
