package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceRankingEntry extends Component {
	public long allianceId;
	public String allianceName;
	public long badge;
	public long region;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsLong(allianceId);
		stream.putString(allianceName);
		stream.putSCID(badge);
		stream.putSCID(region);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		allianceId = stream.getRrsLong();
		allianceName = stream.getString();
		badge = stream.getSCID();
		region = stream.getSCID();
	}
}
