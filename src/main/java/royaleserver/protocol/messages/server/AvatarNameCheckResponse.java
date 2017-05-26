package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class AvatarNameCheckResponse extends Message {
	public static final short ID = Info.AVATAR_NAME_CHECK_RESPONSE;

	public String username;

	public AvatarNameCheckResponse() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(username);
	}
}
