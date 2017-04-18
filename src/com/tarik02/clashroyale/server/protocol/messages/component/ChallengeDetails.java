package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class ChallengeDetails extends Component {
	public int id;

	public ChallengeDetails() {
		id = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(id);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getRrsInt32();
	}
}
