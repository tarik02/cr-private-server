package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;

public class SetNickname extends Command {
	public static final short ID = 201;

	public String nickname;

	public SetNickname() {
		super(ID);

		nickname = "";
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		nickname = stream.getString();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(nickname);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.putRrsInt32(1);
		stream.putRrsInt32(1);
		stream.putRrsInt32(1);

	}

	@Override
	public boolean handle(CommandHandler handler) throws Throwable {
		return handler.handleSetNickname(this);
	}
}
