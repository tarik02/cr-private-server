package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.protocol.messages.StreamCommand;
import royaleserver.utils.DataStream;

public class AllianceStreamComponent extends Component {
	public long entryId;
	public long senderAvatarId;
	public long senderAvatarId2;
	public String senderName;
	public int senderLevel;
	public int senderRole;
	public int ageSeconds;
	public byte isRemoved;

	public StreamCommand command;

	public AllianceStreamComponent() {
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

		stream.putRrsInt32(command.id);

		stream.putRrsLong(entryId);
		stream.putRrsLong(senderAvatarId);
		stream.putRrsLong(senderAvatarId2);
		stream.putString(senderName);
		stream.putRrsInt32(senderLevel);
		stream.putRrsInt32(senderRole);
		stream.putRrsInt32(ageSeconds);
		stream.putByte(isRemoved);

		command.encode(stream);
	}
}
