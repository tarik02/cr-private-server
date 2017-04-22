package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class DonationContainer extends Component {
	public long avatarId;
	public int[] spells;

	public DonationContainer() {
		avatarId = 0;
		spells = new int[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(avatarId);
		spells = new int[stream.getRrsInt32()];
		for (int i = 0; i < spells.length; ++i) {
			stream.putRrsInt32(spells[i]);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		avatarId = stream.getBLong();
		stream.putRrsInt32((int)spells.length);
		for (int i = 0; i < spells.length; ++i) {
			spells[i] = stream.getRrsInt32();
		}
	}
}
