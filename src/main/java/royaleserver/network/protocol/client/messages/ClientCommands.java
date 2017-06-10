package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandFactory;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.utils.DataStream;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public final class ClientCommands extends ClientMessage {
	public static final Logger logger = LogManager.getLogger(ClientCommands.class);

	public static final short ID = Messages.CLIENT_COMMANDS;

	public int tick;
	public int checksum;
	public ClientCommand[] commands;

	public ClientCommands() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new ClientCommands();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClientCommands(this);
	}

	@Override
	public void decode(DataStream stream) {
		tick = stream.getRrsInt32();
		checksum = stream.getRrsInt32();

		commands = new ClientCommand[stream.getRrsInt32()];
		for (int i = 0; i < commands.length; i++) {
			short commandId = (short)stream.getRrsInt32();
			ClientCommand command = ClientCommandFactory.instance.create(commandId);

			if (command == null) {
				logger.warn("Unknown command %d.", commandId);

				break;
			}

			command.decode(stream);
			commands[i] = command;
		}

		stream.skip(4); // Always 0xFFFFFFFF
	}
}
