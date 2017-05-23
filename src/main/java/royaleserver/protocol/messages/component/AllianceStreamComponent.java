package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class AllianceStreamComponent extends Component {
	public int id;
	public long entryId;
	public long senderAvatarId;
	public long senderAvatarId2;
	public String senderName;
	public int senderLevel;
	public int senderRole;
	public int ageSeconds;
	public byte isRemoved;

	public AllianceStreamComponent() {
		id = 0;
		entryId = 0;
		senderAvatarId = 0;
		senderAvatarId2 = 0;
		senderName = "";
		senderLevel = 0;
		senderRole = 0;
		ageSeconds = 0;
		isRemoved = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(id);
		stream.putRrsLong(entryId);
		stream.putRrsLong(senderAvatarId);
		stream.putRrsLong(senderAvatarId2);
		stream.putString(senderName);
		stream.putRrsInt32(senderLevel);
		stream.putRrsInt32(senderRole);
		stream.putRrsInt32(ageSeconds);
		stream.putByte(isRemoved);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getRrsInt32();
		entryId = stream.getRrsLong();
		senderAvatarId = stream.getRrsLong();
		senderAvatarId2 = stream.getRrsLong();
		senderName = stream.getString();
		senderLevel = stream.getRrsInt32();
		senderRole = stream.getRrsInt32();
		ageSeconds = stream.getRrsInt32();
		isRemoved = stream.getByte();
	}
}
