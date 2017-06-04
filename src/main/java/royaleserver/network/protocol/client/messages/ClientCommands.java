package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandFactory;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class ClientCommands extends ClientMessage {
    public static final short ID = Messages.CLIENT_COMMANDS;

    public int tick;
    public int checksum;
    public ClientCommand[] commands;

    public ClientCommands() {
        super(ID);
    }

    @Override
    public void decode(DataStream stream) {
        tick = stream.getRrsInt32();
        checksum = stream.getRrsInt32();

        commands = new ClientCommand[stream.getRrsInt32()];
        for (int i = 0; i < commands.length; i++) {
        	commands[i] = ClientCommandFactory.instance.create((short)stream.getRrsInt32());
        	if (commands[i] == null) {
        		break;
	        }

	        commands[i].decode(stream);
        }
    }

    @Override
    public boolean handle(ClientMessageHandler handler) throws Throwable {
        return handler.handleClientCommands(this);
    }
}
