package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.ServerCommandComponent;

public class AvailableServerCommand extends Message {
	public static final short ID = Info.AVAILABLE_SERVER_COMMAND;

	public ServerCommandComponent command;
	public byte unknown_1;
	public byte unknown_2;
	public byte unknown_3;
	public byte unknown_4;

	public AvailableServerCommand() {
		super(ID);

		command = new ServerCommandComponent();
		unknown_1 = 0;
		unknown_2 = 0;
		unknown_3 = 0;
		unknown_4 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		command.encode(stream);
		stream.putByte(unknown_1);
		stream.putByte(unknown_2);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		command.decode(stream);
		unknown_1 = stream.getByte();
		unknown_2 = stream.getByte();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
	}
}
