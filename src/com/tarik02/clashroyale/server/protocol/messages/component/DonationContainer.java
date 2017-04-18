package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class DonationContainer extends Component {
	public long avatarId;

	public DonationContainer() {
		avatarId = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(avatarId);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		avatarId = stream.getBLong();
	}
}
