package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Handler;
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
	public int unknown_6;
	public int unknown_7;

	public SearchAlliances() {
		super(ID);

		searchString = "";
		desiredRegion = new SCID();
		minMembers = 0;
		maxMembers = 0;
		minTrophies = 0;
		findOnlyJoinableClans = false;
		unknown_6 = 0;
		unknown_7 = 0;
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
		stream.putBInt(unknown_6);
		stream.putBInt(unknown_7);
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
		unknown_6 = stream.getBInt();
		unknown_7 = stream.getBInt();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleSearchAlliances(this);
	}
}
