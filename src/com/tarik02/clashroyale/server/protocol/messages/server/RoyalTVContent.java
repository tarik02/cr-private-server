package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.protocol.messages.component.RoyaleTVEntry;

public class RoyalTVContent extends Message {
	public static final short ID = Info.ROYAL_TV_CONTENT;

	public RoyaleTVEntry[] RoyaleTVEntry;

	public RoyalTVContent() {
		super(ID);

		RoyaleTVEntry = new RoyaleTVEntry[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		RoyaleTVEntry = new RoyaleTVEntry[stream.getByte()];
		for (int i = 0; i < RoyaleTVEntry.length; ++i) {
			RoyaleTVEntry[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		stream.putByte((byte)RoyaleTVEntry.length);
		for (int i = 0; i < RoyaleTVEntry.length; ++i) {
			RoyaleTVEntry[i] = new RoyaleTVEntry();
			RoyaleTVEntry[i].decode(stream);
		}
	}
}
