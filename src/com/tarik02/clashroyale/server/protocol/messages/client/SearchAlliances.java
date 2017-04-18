package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class SearchAlliances extends Message {
	public static final short ID = Info.SEARCH_ALLIANCES;

	public String searchString;
	public SCID desiredRegion;
	public int minMembers;
	public int maxMembers;
	public int minTrophies;
	public boolean findOnlyJoinableClans;

	public SearchAlliances() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(searchString);
		stream.putSCID(desiredRegion);
		stream.putBInt(minMembers);
		stream.putBInt(maxMembers);
		stream.putBInt(minTrophies);
		stream.putBoolean(findOnlyJoinableClans);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		searchString = stream.getString();
		desiredRegion = stream.getSCID();
		minMembers = stream.getBInt();
		maxMembers = stream.getBInt();
		minTrophies = stream.getBInt();
		findOnlyJoinableClans = stream.getBoolean();
	}
}
