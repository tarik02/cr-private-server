package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.utils.DataStream;

public class SetNickname extends Command {
	public static final short ID = 201;

	public String nickname;

	public SetNickname() {
		super(ID);

		nickname = "";
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(nickname);
	}
}
