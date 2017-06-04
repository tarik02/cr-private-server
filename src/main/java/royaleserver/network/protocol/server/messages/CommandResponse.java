package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerCommand;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class CommandResponse extends ServerMessage {
	public static final short ID = Messages.SERVER_COMMAND;

	public ServerCommand command;
	public byte unknown_3;
	public byte unknown_4;

	public CommandResponse() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(command.id);
		command.encode(stream);

		stream.putByte((byte)127);
		stream.putByte((byte)127);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
	}
}
