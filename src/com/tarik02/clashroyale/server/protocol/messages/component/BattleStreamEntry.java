package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class BattleStreamEntry extends Component {
	public long winnerID;
	public String winnerName;
	public String json;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(winnerID);
		stream.putString(winnerName);
		stream.putString(json);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		winnerID = stream.getBLong();
		winnerName = stream.getString();
		json = stream.getString();
	}
}
