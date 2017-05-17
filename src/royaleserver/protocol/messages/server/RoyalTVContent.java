package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.RoyaleTVEntry;

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
