package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class AllianceRankingEntry extends Component {
	public long allianceId;
	public String allianceName;
	public int rank;
	public int score;
	public int previousRank;
	public SCID badge;
	public SCID region;
	public int numberOfMembers;

	public AllianceRankingEntry() {
		allianceId = 0;
		allianceName = "";
		rank = 0;
		score = 0;
		previousRank = 0;
		badge = new SCID();
		region = new SCID();
		numberOfMembers = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsLong(allianceId);
		stream.putString(allianceName);
		stream.putRrsInt32(rank);
		stream.putRrsInt32(score);
		stream.putRrsInt32(previousRank);
		stream.putSCID(badge);
		stream.putSCID(region);
		stream.putRrsInt32(numberOfMembers);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		allianceId = stream.getRrsLong();
		allianceName = stream.getString();
		rank = stream.getRrsInt32();
		score = stream.getRrsInt32();
		previousRank = stream.getRrsInt32();
		badge = stream.getSCID();
		region = stream.getSCID();
		numberOfMembers = stream.getRrsInt32();
	}
}
