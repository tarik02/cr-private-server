package royaleserver.network.protocol.server.messages;

import royaleserver.logic.ClanRole;
import royaleserver.logic.ExpLevel;
import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public class ClanEvent extends ServerMessage {
	public static final short ID = Messages.CLAN_EVENT;

	public static final int EVENT_KICKED = 1;
	public static final int EVENT_LEFT = 2;
	public static final int EVENT_JOINED = 3;
	public static final int EVENT_PROMOTED = 5;

	public long entryId;
	public long senderAvatarId;
	public long senderAvatarId2;
	public String senderName;
	public ExpLevel senderLevel;
	public ClanRole senderRole;
	public int ageSeconds;
	public byte isRemoved;

	public int event;
	public long initiatorId;
	public String initiatorName;

	public ClanEvent() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new ClanEvent();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(4);

		stream.putRrsLong(entryId);
		stream.putRrsLong(senderAvatarId);
		stream.putRrsLong(senderAvatarId2);
		stream.putString(senderName);
		stream.putRrsInt32(senderLevel.getIndex());
		stream.putRrsInt32(senderRole.getIndex());
		stream.putRrsInt32(ageSeconds);
		stream.putByte(isRemoved);

		stream.putRrsInt32(event);
		stream.putRrsLong(initiatorId);
		stream.putString(initiatorName);
	}
}
